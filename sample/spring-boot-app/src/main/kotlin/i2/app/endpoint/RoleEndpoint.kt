package i2.app.endpoint

import i2.f2.role.domain.features.command.RoleAddCompositesFunction
import i2.f2.role.domain.features.command.RoleCreateFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleEndpoint(
    private val roleAddCompositesFunction: RoleAddCompositesFunction,
    private val roleCreateFunction: RoleCreateFunction
) {

    @Bean
    fun roleAddComposites() = roleAddCompositesFunction

    @Bean
    fun roleCreate() = roleCreateFunction
}
