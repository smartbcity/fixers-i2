package i2.f2.role.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleName

typealias RoleAddCompositesFunction = F2Function<RoleAddCompositesCommand, RoleAddCompositesResult>

data class RoleAddCompositesCommand(
    val roleName: RoleName,
    val composites: List<RoleName>,
): Command

data class RoleAddCompositesResult(
    val roleName: RoleName
): Event
