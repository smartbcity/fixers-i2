package i2.init.api.auth

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invokeWith
import i2.keycloak.master.domain.AuthRealm
import i2.init.api.auth.config.KeycloakConfig
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQuery
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQueryFunction
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.command.RealmGetOneQuery
import i2.keycloak.f2.realm.domain.features.command.RealmGetOneQueryFunction
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQuery
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetAllQuery
import i2.keycloak.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQueryFunction
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class KeycloakFinderService(
    private val authRealm: AuthRealm,
    private val keycloakConfig: KeycloakConfig,
    private val clientGetByClientIdentifierQueryFunction: ClientGetByClientIdentifierQueryFunction,
    private val realmGetOneQueryFunction: RealmGetOneQueryFunction,
    private val roleGetByNameQueryFunction: RoleGetByNameQueryFunction,
    private val userGetByEmailQueryFunction: UserGetByEmailQueryFunction,
    private val usetGetAllQueryFunction: UserGetAllQueryFunction
) {
    suspend fun getRealm(id: RealmId): RealmModel? {
        return RealmGetOneQuery(
            id = id,
            authRealm = authRealm
        ).invokeWith(realmGetOneQueryFunction).realm
    }

    suspend fun getClient(id: ClientIdentifier): ClientModel? {
        return ClientGetByClientIdentifierQuery(
            clientIdentifier = id,
            realmId = keycloakConfig.realm,
            auth = authRealm
        ).invokeWith(clientGetByClientIdentifierQueryFunction).client
    }

    suspend fun getRole(name: RoleName): RoleModel? {
        return RoleGetByNameQuery(
            name = name,
            realmId = keycloakConfig.realm,
            auth = authRealm
        ).invokeWith(roleGetByNameQueryFunction).role
    }

    suspend fun getUser(email: String): UserModel? {
        return UserGetByEmailQuery(
            email = email,
            realmId = keycloakConfig.realm,
            auth = authRealm
        ).invokeWith(userGetByEmailQueryFunction).user
    }

    suspend fun getUsers(): List<UserModel> {
        return UserGetAllQuery(
            page = PagePagination(
                size = Int.MAX_VALUE,
                page = 0
            ),
            realmId = keycloakConfig.realm,
            auth = authRealm
        ).invokeWith(usetGetAllQueryFunction).users.list
    }
}
