package i2.f2.organization.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationId

/**
 * Creates a new organization.
 * @d2 section
 * @parent [i2.f2.organization.domain.D2OrganizationCommandSection]
 */
typealias OrganizationCreateFunction = F2Function<OrganizationCreateCommand, OrganizationCreateResult>

/**
 * @d2 command
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreateCommand(
    /**
     * Siret number of the organization.
     * @example [i2.f2.organization.domain.model.Organization.siret]
     */
    val siret: String?,

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
    val roles: List<String>?,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    val parentOrganizationId: OrganizationId? = null
): Command

/**
 * @d2 event
 * @parent [OrganizationCreateFunction]
 */
data class OrganizationCreateResult(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId,

    /**
     * The identifier of a larger organization that this organization is a subOrganization of, if any.
     */
    val parentOrganization: OrganizationId? = null
): Event
