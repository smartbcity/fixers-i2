package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import i2.keycloak.f2.role.domain.RolesCompositesModel
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetRolesFunction = F2Function<UserGetRolesQuery, UserGetRolesResult>

@JsExport
@JsName("UserGetRolesQuery")
class UserGetRolesQuery(
    val userId: UserId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Query

@JsExport
@JsName("UserGetRolesResult")
class UserGetRolesResult(
	val roles: RolesCompositesModel
): Event
