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

typealias UserDisableFunction = F2Function<UserDisableCommand, UserDisabledResult>
typealias UserDisableRemoteFunction = F2FunctionRemote<UserDisableCommand, UserDisabledResult>

@JsExport
@JsName("UserDisableCommand")
class UserDisableCommand(
	val id: UserId,
	val realmId: RealmId,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserDisabledResult")
class UserDisabledResult(
	val id: UserId
) : Event