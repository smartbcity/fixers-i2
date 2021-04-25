package i2.keycloak.realm.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.UserModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetOneQueryFunction = F2Function<UserGetOneQuery, UserGetOneQueryResult>
typealias UserGetOneQueryRemoteFunction = F2FunctionRemote<UserGetOneQuery, UserGetOneQueryResult>

@JsExport
@JsName("UserGetOneQuery")
class UserGetOneQuery(
	open val id: UserId,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserGetOneQueryResult")
class UserGetOneQueryResult(
	val realm: UserModel?
) : Event