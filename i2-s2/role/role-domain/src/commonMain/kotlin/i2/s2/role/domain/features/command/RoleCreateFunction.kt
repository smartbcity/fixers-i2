package i2.s2.role.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.s2.role.domain.RoleId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleCreateFunction = F2Function<RoleCreateCommand, RoleCreatedResult>
typealias RoleCreateRemoteFunction = F2FunctionRemote<RoleCreateCommand, RoleCreatedResult>

@JsExport
@JsName("RoleCreateCommand")
class RoleCreateCommand(
	val id: RoleId,
	val description: String?,
	val isClientRole: Boolean,
	val composites: List<RoleId>,
	val auth: AuthRealm,
): Command

@JsExport
@JsName("RoleCreatedResult")
class RoleCreatedResult(
	val id: String
): Event