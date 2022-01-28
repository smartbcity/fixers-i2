package i2.app.endpoint

import i2.s2.organization.domain.features.command.OrganizationCreateFunction
import i2.s2.organization.domain.features.command.OrganizationUpdateFunction
import i2.s2.organization.domain.features.query.OrganizationGetByIdQueryFunction
import i2.s2.organization.domain.features.query.OrganizationGetInseeOrganizationQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationEndpoint(
    private val organizationCreateFunction: OrganizationCreateFunction,
    private val organizationGetByIdQueryFunction: OrganizationGetByIdQueryFunction,
    private val organizationGetInseeOrganizationQueryFunction: OrganizationGetInseeOrganizationQueryFunction,
    private val organizationUpdateFunction: OrganizationUpdateFunction
) {

    @Bean
    fun createOrganization() = organizationCreateFunction

    @Bean
    fun updateOrganization() = organizationUpdateFunction

    @Bean
    fun getOrganization() = organizationGetByIdQueryFunction

    @Bean
    fun getInseeOrganization() = organizationGetInseeOrganizationQueryFunction
}
