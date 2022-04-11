package i2.f2.organization.domain.model

interface OrganizationRefDTO {
    val id: OrganizationId
    val name: String
}

data class OrganizationRef(
    override val id: OrganizationId,
    override val name: String,
): OrganizationRefDTO
