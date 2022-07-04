package i2.keycloak.f2.role.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleUpdateFunction = F2Function<RoleUpdateCommand, RoleUpdateResult>

@JsExport
@JsName("RoleUpdateCommand")
class RoleUpdateCommand(
    val name: RoleName,
    val description: String?,
    val isClientRole: Boolean,
    val composites: List<RoleName>,
    override val auth: AuthRealm,
    val realmId: RealmId
): KeycloakF2Command

@JsExport
@JsName("RoleUpdateResult")
class RoleUpdateResult(
    val id: RoleId
): KeycloakF2Result
