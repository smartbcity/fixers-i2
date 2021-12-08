package i2.keycloak.realm.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserId
import i2.s2.commons.f2.KeycloakF2Command
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetRolesQueryFunction = F2Function<UserGetRolesQuery, UserGetRolesQueryResult>

@JsExport
@JsName("UserGetRolesQuery")
class UserGetRolesQuery(
	val userId: UserId,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserGetRolesQueryResult")
class UserGetRolesQueryResult(
	val roles: List<String>
): Event
