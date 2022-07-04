package i2.keycloak.f2.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdateResult>

@JsExport
@JsName("UserUpdateCommand")
class UserUpdateCommand(
    val userId: UserId,
    val realmId: RealmId,
    val auth: AuthRealm,
    val firstname: String?,
    val lastname: String?,
    val email: String,
    val metadata: Map<String, String>,
): Command

@JsExport
@JsName("UserUpdateResult")
class UserUpdateResult(
	val id: UserId
): Event
