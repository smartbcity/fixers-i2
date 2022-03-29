package i2.f2.role.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName

typealias RoleCreateFunction = F2Function<RoleCreateCommand, RoleCreatedResult>

data class RoleCreateCommand(
    val name: RoleName,
    val composites: List<RoleName>,
    val description: String?,
    val isClientRole: Boolean
): Command

data class RoleCreatedResult(
    val id: RoleId
): Event
