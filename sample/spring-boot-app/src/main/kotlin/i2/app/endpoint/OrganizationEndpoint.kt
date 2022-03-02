package i2.app.endpoint

import i2.f2.organization.domain.features.command.OrganizationCreateFunction
import i2.f2.organization.domain.features.command.OrganizationUpdateFunction
import i2.f2.organization.domain.features.query.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationEndpoint(
    private val organizationCreateFunction: OrganizationCreateFunction,
    private val organizationGetAllQueryFunction: OrganizationGetAllQueryFunction,
    private val organizationGetByIdQueryFunction: OrganizationGetByIdQueryFunction,
    private val organizationGetInseeOrganizationQueryFunction: OrganizationGetInseeOrganizationQueryFunction,
    private val organizationUpdateFunction: OrganizationUpdateFunction,
    private val organizationRefGetAllQueryFunction: OrganizationRefGetAllQueryFunction,
    private val organizationGetPageQueryFunction: OrganizationGetPageQueryFunction
) {

    @Bean
    fun createOrganization() = organizationCreateFunction

    @Bean
    fun updateOrganization() = organizationUpdateFunction

    @Bean
    fun getOrganization() = organizationGetByIdQueryFunction

    @Bean
    fun getInseeOrganization() = organizationGetInseeOrganizationQueryFunction

    @Bean
    fun getAllOrganizations() = organizationGetAllQueryFunction

    @Bean
    fun getOrganizationPage() = organizationGetPageQueryFunction

    @Bean
    fun getAllOrganizationRefs() = organizationRefGetAllQueryFunction
}
