package i2.keycloak.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserCreateFunction = F2Function<UserCreateCommand, UserCreatedResult>
typealias UserCreateRemoteFunction = F2FunctionRemote<UserCreateCommand, UserCreatedResult>

@JsExport
@JsName("UserCreateCommand")
class UserCreateCommand(
	val realmId: RealmId,
	val username: String,
	val firstname: String?,
	val lastname: String?,
	val email: String,
	val isEnable: Boolean,
	val metadata: Map<String, String>,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserCreatedResult")
class UserCreatedResult(
	val id: UserId
) : Event