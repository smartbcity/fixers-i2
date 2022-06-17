package i2.f2.organization.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationId

/**
 * Updates an organization.
 * @d2 section
 * @parent [i2.f2.organization.domain.D2OrganizationCommandSection]
 */
typealias OrganizationUpdateFunction = F2Function<OrganizationUpdateCommand, OrganizationUpdateResult>

/**
 * @d2 command
 * @parent [OrganizationUpdateFunction]
 */
data class OrganizationUpdateCommand(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId,

    /**
     * Official name of the organization.
     * @example [i2.f2.organization.domain.model.Organization.name]
     */
    val name: String,

    /**
     * Description of the organization.
     * @example [i2.f2.organization.domain.model.Organization.description]
     */
    val description: String?,

    /**
     * Address of the organization.
     */
    val address: AddressBase?,

    /**
     * Website of the organization.
     * @example [i2.f2.organization.domain.model.Organization.website]
     */
    val website: String?,

    /**
     * Effective roles assigned to the organization. Multiple effective roles can be contained in a role.
     * @example [i2.f2.organization.domain.model.Organization.roles]
     */
    val roles: List<String>?
): Command

/**
 * @d2 event
 * @parent [OrganizationUpdateFunction]
 */
data class OrganizationUpdateResult(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId
): Event
