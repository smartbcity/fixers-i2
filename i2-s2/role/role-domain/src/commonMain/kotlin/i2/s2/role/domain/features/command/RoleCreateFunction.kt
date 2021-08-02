package i2.s2.role.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.commons.f2.KeycloakF2Command
import i2.s2.commons.f2.KeycloakF2Result
import i2.s2.role.domain.RoleId
import i2.s2.role.domain.RoleName
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleCreateFunction = F2Function<RoleCreateCommand, RoleCreatedResult>

@JsExport
@JsName("RoleCreateCommand")
class RoleCreateCommand(
	val name: RoleName,
	val description: String?,
	val isClientRole: Boolean,
	val composites: List<RoleName>,
	override val auth: AuthRealm,
	val realmId: RealmId
): KeycloakF2Command

@JsExport
@JsName("RoleCreatedResult")
class RoleCreatedResult(
	val id: RoleId
): KeycloakF2Result