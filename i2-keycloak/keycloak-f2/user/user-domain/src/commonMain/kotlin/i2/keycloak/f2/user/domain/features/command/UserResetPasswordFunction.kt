package i2.keycloak.f2.user.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserPasswordResetResult>

@JsExport
@JsName("UserResetPasswordCommand")
class UserResetPasswordCommand(
	val userId: UserId,
	val password: String,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserPasswordResetResult")
class UserPasswordResetResult(
	val userId: UserId
): KeycloakF2Result
