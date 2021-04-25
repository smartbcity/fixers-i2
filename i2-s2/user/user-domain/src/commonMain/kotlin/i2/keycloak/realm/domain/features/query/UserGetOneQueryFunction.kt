package i2.keycloak.realm.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.s2.realm.domain.RealmId
import i2.s2.realm.domain.RealmModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmGetOneQueryFunction = F2Function<RealmGetOneQuery, RealmGetOneQueryResult>
typealias RealmGetOneQueryRemoteFunction = F2FunctionRemote<RealmGetOneQuery, RealmGetOneQueryResult>

@JsExport
@JsName("RealmGetOneQuery")
class RealmGetOneQuery(
	open val id: RealmId,
	val authRealm: AuthRealm
) : Command

@JsExport
@JsName("RealmGetOneQueryResult")
class RealmGetOneQueryResult(
	val realm: RealmModel?
) : Event