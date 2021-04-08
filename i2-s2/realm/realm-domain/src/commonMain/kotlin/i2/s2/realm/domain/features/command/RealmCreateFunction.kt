package i2.s2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmCreateFunction = F2Function<RealmCreateCommand, RealmCreatedResult>
typealias RealmCreateRemoteFunction = F2FunctionRemote<RealmCreateCommand, RealmCreatedResult>

@JsExport
@JsName("RealmCreateCommand")
class RealmCreateCommand(
	val id: String,
	val theme: String?,
	val locale: String?,
	val smtpServer: Map<String, String>?,
	val masterRealmAuth: AuthRealm
) : Command

@JsExport
@JsName("RealmCreatedResult")
class RealmCreatedResult(
	val id: String
) : Event