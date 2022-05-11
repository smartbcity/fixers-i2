package i2.f2.organization.domain.model

import i2.commons.model.Address
import i2.commons.model.AddressBase
import i2.keycloak.f2.group.domain.model.GroupId

/**
 * Unique identifier of an organization.
 * @d2 model
 * @parent [i2.f2.organization.domain.D2OrganizationModelSection]
 * @order 20
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias OrganizationId = GroupId

/**
 * Representation of an organization.
 * @D2 model
 * @parent [i2.f2.organization.domain.D2OrganizationModelSection]
 */
interface Organization {
    val id: OrganizationId
    val siret: String
    val name: String
    val description: String?
    val address: Address?
    val website: String?
    val roles: Array<String>?
}

data class OrganizationBase(
    override val id: OrganizationId,
    override val siret: String,
    override val name: String,
    override val description: String?,
    override val address: AddressBase?,
    override val website: String?,
    override val roles: Array<String>?
): Organization
