package i2.f2.organization.domain.model

interface OrganizationRefDTO {
    val id: OrganizationId
    val name: String
    val roles: List<String>
}

data class OrganizationRef(
    override val id: OrganizationId,
    override val name: String,
    override val roles: List<String>
): OrganizationRefDTO
