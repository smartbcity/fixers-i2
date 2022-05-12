package i2.f2.role.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleName

/**
 * Adds composites roles to a role.
 * @d2 section
 * @parent [i2.f2.role.domain.D2RoleCommandSection]
 */
typealias RoleAddCompositesFunction = F2Function<RoleAddCompositesCommand, RoleAddCompositesResult>

/**
 * @d2 command
 * @parent [RoleAddCompositesFunction]
 */
data class RoleAddCompositesCommand(
    /**
     * Name of the role to add the composites roles to.
     * @example "Carrier"
     */
    val roleName: RoleName,

    /**
     * List of roles that are associated with the role.
     * @example [["write_user", "read_user"]]
     */
    val composites: List<RoleName>,
): Command

/**
 * @d2 event
 * @parent [RoleAddCompositesFunction]
 */
data class RoleAddCompositesResult(
    /**
     * Name of the role that composites were added to.
     * @example [RoleAddCompositesCommand.roleName]
     */
    val roleName: RoleName
): Event
