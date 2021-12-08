package i2.s2.user.f2.service

import f2.dsl.fnc.invokeWith
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.query.UserGetRolesQuery
import i2.keycloak.realm.domain.features.query.UserGetRolesQueryFunction
import org.springframework.stereotype.Service

@Service
internal class UserFinderService(
    private val userGetRolesQueryFunction: UserGetRolesQueryFunction
) {
    suspend fun getRoles(userId: UserId, realmId: RealmId, authRealm: AuthRealm) = UserGetRolesQuery(
        userId = userId,
        realmId = realmId,
        auth = authRealm
    ).invokeWith(userGetRolesQueryFunction).roles
}
