package i2.keycloak.realm.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetByEmailQueryFunction = F2Function<UserGetByEmailQuery, UserGetByEmailQueryResult>

@JsExport
@JsName("UserGetByEmailQuery")
class UserGetByEmailQuery(
	val realmId: RealmId,
	val auth: AuthRealm,
	val email: String
): Command

@JsExport
@JsName("UserGetByEmailQueryResult")
class UserGetByEmailQueryResult(
	val user: UserModel?
): Event
