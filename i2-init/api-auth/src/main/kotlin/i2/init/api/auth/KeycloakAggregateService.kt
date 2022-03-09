package i2.init.api.auth

import f2.dsl.fnc.invokeWith
import i2.init.api.auth.config.KeycloakConfig
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.realm.domain.features.command.RealmCreateCommand
import i2.keycloak.f2.realm.domain.features.command.RealmCreateFunction
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.command.RoleAddCompositesCommand
import i2.keycloak.f2.role.domain.features.command.RoleAddCompositesFunction
import i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
import i2.keycloak.f2.role.domain.features.command.RoleCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.keycloak.f2.user.domain.features.command.UserCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class KeycloakAggregateService(
    private val authRealm: AuthRealm,
    private val clientCreateFunction: ClientCreateFunction,
    private val keycloakConfig: KeycloakConfig,
    private val realmCreateFunction: RealmCreateFunction,
    private val roleAddCompositesFunction: RoleAddCompositesFunction,
    private val roleCreateFunction: RoleCreateFunction,
    private val userCreateFunction: UserCreateFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction
) {

    suspend fun createRealm(id: RealmId, smtpConfig: Map<String, String>): RealmId {
        return RealmCreateCommand(
            id = id,
            theme = null,
            locale = null,
            smtpServer = smtpConfig,
            masterRealmAuth = authRealm,
        ).invokeWith(realmCreateFunction).id
    }

    suspend fun createClient(
        identifier: ClientIdentifier,
        secret: String? = null,
        isPublic: Boolean = true,
        isDirectAccessGrantsEnabled: Boolean = true,
        isServiceAccountsEnabled: Boolean = true,
        authorizationServicesEnabled: Boolean = false,
        isStandardFlowEnabled: Boolean = false,
        baseUrl: String? = null,
        localhostUrl: String? = null,
        protocolMappers: Map<String, String> = emptyMap(),
    ): ClientId {
        return ClientCreateCommand(
            auth = authRealm,
            realmId = keycloakConfig.realm,
            clientIdentifier = identifier,
            secret = secret,
            isPublicClient = isPublic,
            isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled = isServiceAccountsEnabled,
            authorizationServicesEnabled = authorizationServicesEnabled,
            isStandardFlowEnabled = isStandardFlowEnabled,
            rootUrl = baseUrl,
            redirectUris = listOfNotNull(baseUrl, localhostUrl),
            baseUrl = baseUrl ?: "",
            adminUrl = baseUrl ?: "",
            webOrigins = listOfNotNull(baseUrl, localhostUrl),
            protocolMappers = protocolMappers,
        ).invokeWith(clientCreateFunction).id
    }

    suspend fun createRole(name: RoleName): RoleId {
        return RoleCreateCommand(
            name = name,
            description = null,
            isClientRole = false,
            composites = emptyList(),
            auth = authRealm,
            realmId = keycloakConfig.realm
        ).invokeWith(roleCreateFunction).id
    }

    suspend fun addRoleComposites(role: RoleName, composites: List<RoleName>): String {
        return RoleAddCompositesCommand(
            roleName = role,
            composites = composites,
            auth = authRealm,
            realmId = keycloakConfig.realm
        ).invokeWith(roleAddCompositesFunction).id
    }

    suspend fun createUser(
        username: String,
        email: String,
        firstname: String,
        lastname: String,
        isEnable: Boolean,
        metadata: Map<String, String> = emptyMap(),
    ): UserId {
        return UserCreateCommand(
            username = username,
            email = email,
            firstname = firstname,
            lastname = lastname,
            isEnable = isEnable,
            metadata = metadata,
            auth = authRealm,
            realmId = keycloakConfig.realm
        ).invokeWith(userCreateFunction).id
    }

    suspend fun grantUser(id: UserId, vararg roles: RoleName) {
        UserRolesGrantCommand(
            id = id,
            roles = roles.toList(),
            auth = authRealm,
            realmId = keycloakConfig.realm
        ).invokeWith(userRolesGrantFunction)
    }
}
