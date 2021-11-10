package i2.keycloak.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.realm.domain.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserResetPasswordResult>

@JsExport
@JsName("UserResetPasswordCommand")
class UserResetPasswordCommand(
	val id: UserId,
	val password: String?,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserResetPasswordResult")
class UserResetPasswordResult(
	val id: String
) : Event