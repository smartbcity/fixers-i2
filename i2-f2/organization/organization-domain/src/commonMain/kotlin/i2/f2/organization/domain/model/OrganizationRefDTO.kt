package i2.f2.organization.domain.model

/**
 * Representation of an organization.
 * @D2 model
 * @parent [i2.f2.organization.domain.D2OrganizationModelSection]
 */
interface OrganizationRefDTO {
    /**
     * Identifier of an organization.
     */
    val id: OrganizationId
    val name: String
    val roles: List<String>
}

data class OrganizationRef(
    override val id: OrganizationId,
    override val name: String,
    override val roles: List<String>
): OrganizationRefDTO
