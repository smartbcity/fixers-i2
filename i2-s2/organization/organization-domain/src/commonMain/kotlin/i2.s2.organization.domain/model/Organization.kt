package i2.s2.organization.domain.model

import i2.s2.group.domain.model.GroupId

typealias OrganizationId = GroupId

interface Organization {
    val id: OrganizationId
    val siret: String
    val name: String
    val description: String?
    val address: Address
}

data class OrganizationBase(
    override val id: OrganizationId,
    override val siret: String,
    override val name: String,
    override val description: String?,
    override val address: Address
): Organization
