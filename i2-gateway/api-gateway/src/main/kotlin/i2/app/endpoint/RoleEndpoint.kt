package i2.app.endpoint

import i2.app.auth.SUPER_ADMIN_ROLE
import i2.f2.role.domain.features.command.RoleAddCompositesFunction
import i2.f2.role.domain.features.command.RoleCreateFunction
import i2.f2.role.domain.features.command.RoleUpdateFunction
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 service
 * @title Role/Entrypoints
 */
@Configuration
class RoleEndpoint(
    private val roleAddCompositesFunction: RoleAddCompositesFunction,
    private val roleCreateFunction: RoleCreateFunction,
    private val roleUpdateFunction: RoleUpdateFunction
) {

    /**
     * Associates roles to another role. Associated roles must exist.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleAddComposites() = roleAddCompositesFunction

    /**
     * Creates a Role.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleCreate() = roleCreateFunction

    /**
     * Updates a Role.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleUpdate() = roleUpdateFunction
}
