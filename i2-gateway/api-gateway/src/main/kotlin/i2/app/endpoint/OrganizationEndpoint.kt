package i2.app.endpoint

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.app.auth.PermissionEvaluator
import i2.app.auth.SUPER_ADMIN_ROLE
import i2.f2.organization.domain.features.command.OrganizationCreateFunction
import i2.f2.organization.domain.features.command.OrganizationUpdateFunction
import i2.f2.organization.domain.features.query.OrganizationGetAllQueryFunction
import i2.f2.organization.domain.features.query.OrganizationGetByIdQueryFunction
import i2.f2.organization.domain.features.query.OrganizationGetInseeOrganizationQueryFunction
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQueryFunction
import javax.annotation.security.RolesAllowed
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
    private val permissionEvaluator: PermissionEvaluator
) {

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun createOrganization() = organizationCreateFunction

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "write_organization")
    fun updateOrganization(): OrganizationUpdateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.id)) {
            organizationUpdateFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "read_organization")
    fun getOrganization() = organizationGetByIdQueryFunction

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "read_organization")
    fun getInseeOrganization() = organizationGetInseeOrganizationQueryFunction

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "read_organization")
    fun getAllOrganizations() = organizationGetAllQueryFunction

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "read_organization")
    fun getAllOrganizationRefs() = organizationRefGetAllQueryFunction
}
