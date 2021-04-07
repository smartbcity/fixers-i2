package i2.keycloak.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.realm.domain.ServiceRealmAuth
import i2.keycloak.realm.domain.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserResetPasswordResult>
typealias UserResetPasswordRemoteFunction = F2FunctionRemote<UserResetPasswordCommand, UserResetPasswordResult>

@JsExport
@JsName("UserResetPasswordCommand")
class UserResetPasswordCommand(
	val id: UserId,
	val password: String?,
	val auth: ServiceRealmAuth,
) : Command

@JsExport
@JsName("UserResetPasswordResult")
class UserResetPasswordResult(
	val id: String
) : Event