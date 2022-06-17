package i2.config.api.auth

import f2.dsl.fnc.invokeWith
import i2.config.api.auth.config.KeycloakAdminConfig
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQuery
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQuery
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import org.springframework.stereotype.Service

@Service
class KeycloakFinderService(
    private val authRealm: AuthRealm,
    private val keycloakAdminConfig: KeycloakAdminConfig,
    private val clientGetByClientIdentifierQueryFunction: ClientGetByClientIdentifierFunction,
    private val roleGetByNameQueryFunction: RoleGetByNameQueryFunction,
    private val userGetByEmailQueryFunction: UserGetByEmailFunction,
) {

    suspend fun getClient(id: ClientIdentifier): ClientModel? {
        return ClientGetByClientIdentifierQuery(
            clientIdentifier = id,
            realmId = keycloakAdminConfig.realm,
            auth = authRealm
        ).invokeWith(clientGetByClientIdentifierQueryFunction).client
    }

    suspend fun getRole(name: RoleName): RoleModel? {
        return RoleGetByNameQuery(
            name = name,
            realmId = keycloakAdminConfig.realm,
            auth = authRealm
        ).invokeWith(roleGetByNameQueryFunction).role
    }

    suspend fun getUser(email: String): UserModel? {
        return UserGetByEmailQuery(
            email = email,
            realmId = keycloakAdminConfig.realm,
            auth = authRealm
        ).invokeWith(userGetByEmailQueryFunction).user
    }
}
