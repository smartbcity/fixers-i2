package i2.app.endpoint

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.app.auth.PermissionEvaluator
import i2.app.auth.SUPER_ADMIN_ROLE
import i2.f2.organization.domain.features.command.OrganizationCreateFunction
import i2.f2.organization.domain.features.command.OrganizationUpdateFunction
import i2.f2.organization.domain.features.query.OrganizationGetBySiretFunction
import i2.f2.organization.domain.features.query.OrganizationGetFunction
import i2.f2.organization.domain.features.query.OrganizationPageFunction
import i2.f2.organization.domain.features.query.OrganizationRefGetAllFunction
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 service
 * @title Organization/Entrypoints
 */
@Configuration
class OrganizationEndpoint(
    private val organizationCreateFunction: OrganizationCreateFunction,
    private val organizationPageFunction: OrganizationPageFunction,
    private val organizationGetFunction: OrganizationGetFunction,
    private val organizationGetBySiretFunction: OrganizationGetBySiretFunction,
    private val organizationUpdateFunction: OrganizationUpdateFunction,
    private val organizationRefGetAllFunction: OrganizationRefGetAllFunction,
    private val permissionEvaluator: PermissionEvaluator
) {

    /**
     * Creates an Organization.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun organizationCreate() = organizationCreateFunction

    /**
     * Updates an Organization.
     */
    @Bean
    @RolesAllowed("write_organization")
    fun organizationUpdate(): OrganizationUpdateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.id)) {
            organizationUpdateFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Fetches an Organization by its ID.
     */
    @Bean
    @RolesAllowed("read_organization")
    fun organizationGet() = organizationGetFunction

    /**
     * Fetches an Organization by its siret number.
     */
    @Bean
    @RolesAllowed("read_organization")
    fun organizationGetBySiret() = organizationGetBySiretFunction

    /**
     * Fetches a page of organizations.
     */
    @Bean
    @RolesAllowed("read_organization")
    fun organizationPage() = organizationPageFunction

    /**
     * Fetches all OrganizationRef.
     */
    @Bean
    @RolesAllowed("read_organization")
    fun organizationRefGetAll() = organizationRefGetAllFunction
}
