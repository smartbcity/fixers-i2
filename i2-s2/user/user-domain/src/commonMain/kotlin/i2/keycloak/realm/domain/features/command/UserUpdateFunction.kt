package i2.keycloak.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdatedResult>

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
@JsName("UserUpdatedResult")
class UserUpdatedResult(
	val id: UserId
): Event