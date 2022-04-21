package i2.app.endpoint

import i2.app.auth.SUPER_ADMIN_ROLE
import i2.f2.role.domain.features.command.RoleAddCompositesFunction
import i2.f2.role.domain.features.command.RoleCreateFunction
import i2.f2.role.domain.features.command.RoleUpdateFunction
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleEndpoint(
    private val roleAddCompositesFunction: RoleAddCompositesFunction,
    private val roleCreateFunction: RoleCreateFunction,
    private val roleUpdateFunction: RoleUpdateFunction
) {

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleAddComposites() = roleAddCompositesFunction

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleCreate() = roleCreateFunction

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleUpdate() = roleUpdateFunction
}
