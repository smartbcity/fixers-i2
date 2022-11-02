package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import i2.keycloak.f2.user.domain.model.UserGroup
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetGroupsFunction = F2Function<UserGetGroupsQuery, UserGetGroupsResult>

@JsExport
@JsName("UserGetGroupsQuery")
class UserGetGroupsQuery(
	val userId: UserId,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Query

@JsExport
@JsName("UserGetGroupsResult")
class UserGetGroupsResult(
	val items: List<UserGroup>
): Event
