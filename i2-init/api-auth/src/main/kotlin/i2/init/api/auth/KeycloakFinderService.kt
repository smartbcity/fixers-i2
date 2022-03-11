package i2.init.api.auth

import f2.dsl.fnc.invokeWith
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQuery
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQueryFunction
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.command.RealmGetOneQuery
import i2.keycloak.f2.realm.domain.features.command.RealmGetOneQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQueryFunction
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class KeycloakFinderService(
    private val authRealm: AuthRealm,
    private val clientGetByClientIdentifierQueryFunction: ClientGetByClientIdentifierQueryFunction,
    private val realmGetOneQueryFunction: RealmGetOneQueryFunction,
    private val userGetByEmailQueryFunction: UserGetByEmailQueryFunction
) {
    suspend fun getRealm(id: RealmId): RealmModel? {
        return RealmGetOneQuery(
            id = id,
            authRealm = authRealm
        ).invokeWith(realmGetOneQueryFunction).realm
    }

    suspend fun getClient(id: ClientIdentifier, realmId: RealmId): ClientModel? {
        return ClientGetByClientIdentifierQuery(
            clientIdentifier = id,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(clientGetByClientIdentifierQueryFunction).client
    }

    suspend fun getUser(email: String, realmId: RealmId): UserModel? {
        return UserGetByEmailQuery(
            email = email,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(userGetByEmailQueryFunction).user
    }
}
