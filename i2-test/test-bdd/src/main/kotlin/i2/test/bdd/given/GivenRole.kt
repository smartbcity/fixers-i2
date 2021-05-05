package i2.test.bdd.given

import f2.function.spring.invokeSingle
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.s2.role.domain.RoleName
import i2.s2.role.domain.features.command.RoleCreateCommand
import i2.s2.role.f2.RoleCreateFunctionImpl
import kotlinx.coroutines.runBlocking

class GivenRole(
	val client: AuthRealmClient
) {
	fun withRole(realmId: RealmId, roleId: RoleName, composite: List<RoleName> = emptyList()): RoleName = runBlocking {
		val cmd = RoleCreateCommand(
			name = roleId,
			description = "description",
			isClientRole = false,
			composites = composite,
			auth = client.auth,
			realmId = realmId
		)
		RoleCreateFunctionImpl().roleCreateFunction().invokeSingle(cmd).id
	}
}

fun GivenKC.role() = GivenRole(client)