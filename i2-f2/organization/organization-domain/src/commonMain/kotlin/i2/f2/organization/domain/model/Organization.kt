package i2.f2.organization.domain.model

import i2.keycloak.f2.group.domain.model.GroupId

typealias OrganizationId = GroupId

interface Organization {
    val id: OrganizationId
    val siret: String
    val name: String
    val description: String?
    val address: Address
    val website: String?
}

data class OrganizationBase(
    override val id: OrganizationId,
    override val siret: String,
    override val name: String,
    override val description: String?,
    override val address: Address,
    override val website: String?
): Organization
