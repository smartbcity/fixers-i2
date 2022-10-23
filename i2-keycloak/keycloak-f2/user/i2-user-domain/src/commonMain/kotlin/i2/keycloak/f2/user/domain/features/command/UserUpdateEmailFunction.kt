package i2.keycloak.f2.user.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserUpdateEmailFunction = F2Function<UserUpdateEmailCommand, UserUpdatedEmailEvent>

@JsExport
@JsName("UserUpdateEmailCommand")
class UserUpdateEmailCommand(
    val userId: UserId,
    val email: String,
    val sendVerificationEmail: Boolean,
    val clientId: String? = null,
    val redirectUri: String? = null,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserUpdatedEmailEvent")
class UserUpdatedEmailEvent(
	val userId: UserId
): KeycloakF2Result
