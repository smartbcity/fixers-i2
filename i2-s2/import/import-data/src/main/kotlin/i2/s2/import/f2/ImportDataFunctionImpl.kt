package i2.s2.import.f2

import f2.function.spring.adapter.f2Function
import f2.function.spring.invokeSingle
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.command.UserCreateFunction
import i2.keycloak.realm.domain.features.command.UserRolesGrantCommand
import i2.keycloak.realm.domain.features.command.UserRolesGrantFunction
import i2.keycloak.realm.domain.features.query.UserGetByUsernameQuery
import i2.keycloak.realm.domain.features.query.UserGetByUsernameQueryFunction
import i2.s2.client.domain.ClientId
import i2.s2.client.domain.ClientIdentifier
import i2.s2.client.domain.features.command.ClientCreateFunction
import i2.s2.realm.domain.ClientImport
import i2.s2.realm.domain.RealmId
import i2.s2.realm.domain.RealmImport
import i2.s2.realm.domain.RoleImport
import i2.s2.realm.domain.UserImport
import i2.s2.realm.domain.features.command.ImportDataFunction
import i2.s2.realm.domain.features.command.ImportDataResult
import i2.s2.realm.domain.features.command.RealmCreateFunction
import i2.s2.realm.domain.features.command.RealmCreatedResult
import i2.s2.realm.domain.toClientCreateCommand
import i2.s2.realm.domain.toRealmCreateCommand
import i2.s2.realm.domain.toRoleAddCompositesCommand
import i2.s2.realm.domain.toRoleCreateCommandWithoutComposites
import i2.s2.realm.domain.toUserCreateCommand
import i2.s2.role.domain.RoleName
import i2.s2.role.domain.features.command.RoleAddCompositesFunction
import i2.s2.role.domain.features.command.RoleCreateFunction
import i2.s2.role.domain.features.command.RoleCreatedResult
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ImportDataFunctionImpl {

	@Autowired
	private lateinit var clientCreateFunction: ClientCreateFunction

	@Autowired
	private lateinit var realmCreateFunction: RealmCreateFunction

	@Autowired
	private lateinit var roleCreateFunction: RoleCreateFunction

	@Autowired
	private lateinit var roleAddCompositesFunction: RoleAddCompositesFunction

	@Autowired
	private lateinit var userCreateFunction: UserCreateFunction

	@Autowired
	private lateinit var userGetByUsernameQueryFunction: UserGetByUsernameQueryFunction

	@Autowired
	private lateinit var userRolesGrantFunction: UserRolesGrantFunction

	companion object {
		const val SERVICE_ACCOUNT_PREFIX = "service-account-"
	}

	@Bean
	fun importDataFunction(): ImportDataFunction = f2Function { cmd ->
		val realmId = createRealm(cmd.auth, cmd.realmImport).id
		val roles = createRoles(cmd.auth, realmId, cmd.realmImport.roles)
		val clients = createClients(cmd.auth, realmId, cmd.realmImport.clients)
		val users = createUsers(cmd.auth, realmId, cmd.realmImport.users)

		ImportDataResult(
			realmId = realmId,
			roleIds = roles.map(RoleCreatedResult::id),
			clientIdPerIdentifiers = clients,
			userIdPerUsernames = users
		)
	}

	private suspend fun createRealm(auth: AuthRealm, realmImport: RealmImport): RealmCreatedResult {
		val command = realmImport.toRealmCreateCommand(auth)
		return realmCreateFunction.invokeSingle(command)
	}

	private suspend fun createRoles(auth: AuthRealm, realmId: RealmId, roleImports: List<RoleImport>): List<RoleCreatedResult> {
		val createCommands = roleImports.map { role -> role.toRoleCreateCommandWithoutComposites(auth, realmId) }.asFlow()
		val roleResults = roleCreateFunction(createCommands).toList()

		val addCompositesCommand = roleImports.map { role -> role.toRoleAddCompositesCommand(auth, realmId) }.asFlow()
		roleAddCompositesFunction(addCompositesCommand)

		return roleResults
	}

	private suspend fun createClients(auth: AuthRealm, realmId: RealmId, clientImports: List<ClientImport>): Map<ClientId, ClientIdentifier> {
		return clientImports.map { clientImport ->
			val createCommand = clientImport.toClientCreateCommand(auth, realmId)
			val result = clientCreateFunction.invokeSingle(createCommand)
			grantServiceAccountRoles(auth, realmId, clientImport)
			result.id to clientImport.clientIdentifier
		}.toMap()
	}

	private suspend fun grantServiceAccountRoles(auth: AuthRealm, realmId: RealmId, clientImport: ClientImport) {
		if (!clientImport.isServiceAccountsEnabled || clientImport.serviceAccountRoles.isEmpty()) {
			return
		}

		val serviceAccountId = getUserIdByUsername(auth, realmId, "$SERVICE_ACCOUNT_PREFIX${clientImport.clientIdentifier}")!!
		grantUser(auth, realmId, serviceAccountId, clientImport.serviceAccountRoles)
	}

	private suspend fun getUserIdByUsername(auth: AuthRealm, realmId: RealmId, username: String): UserId? {
		val query = UserGetByUsernameQuery(
			auth = auth,
			realmId = realmId,
			username = username
		)
		return userGetByUsernameQueryFunction.invokeSingle(query).user?.id
	}

	private suspend fun createUsers(auth: AuthRealm, realmId: RealmId, userImports: List<UserImport>): Map<UserId, String> {
		return userImports.map { userImport ->
			val command = userImport.toUserCreateCommand(auth, realmId)
			val result = userCreateFunction.invokeSingle(command)
			grantUser(auth, realmId, result.id, userImport.roles)
			result.id to userImport.username
		}.toMap()
	}

	private suspend fun grantUser(auth: AuthRealm, realmId: RealmId, userId: UserId, roles: List<RoleName>) {
		val rolesCommand = UserRolesGrantCommand(
			id = userId,
			roles = roles,
			auth = auth,
			realmId = realmId
		)
		userRolesGrantFunction.invokeSingle(rolesCommand)
	}
}
