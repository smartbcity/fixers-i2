package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetByEmailFunction = F2Function<UserGetByEmailQuery, UserGetByEmailResult>

@JsExport
@JsName("UserGetByEmailQuery")
class UserGetByEmailQuery(
    val email: String,
    val realmId: RealmId,
    override val auth: AuthRealm
): KeycloakF2Query

@JsExport
@JsName("UserGetByEmailQueryResult")
class UserGetByEmailResult(
	val item: UserModel?
): Event
