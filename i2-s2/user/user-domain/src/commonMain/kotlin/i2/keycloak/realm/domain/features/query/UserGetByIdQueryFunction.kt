package i2.keycloak.realm.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.UserModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetByIdQueryFunction = F2Function<UserGetByIdQuery, UserGetByIdQueryResult>

@JsExport
@JsName("UserGetByIdQuery")
class UserGetByIdQuery(
	val id: UserId,
	val realmId: RealmId,
	val auth: AuthRealm,
): Command

@JsExport
@JsName("UserGetByIdQueryResult")
class UserGetByIdQueryResult(
	val user: UserModel?
): Event