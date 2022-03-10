package i2.f2.organization.domain.model

interface OrganizationRef {
    val id: OrganizationId
    val name: String
}

data class OrganizationRefBase(
    override val id: OrganizationId,
    override val name: String,
): OrganizationRef
