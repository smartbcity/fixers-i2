package i2.s2.role.f2

import i2.s2.commons.f2.keycloakF2Function
import i2.s2.role.domain.features.command.RoleAddCompositesFunction
import i2.s2.role.domain.features.command.RoleAddCompositesResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleAddCompositesFunctionImpl {

    @Bean
    fun roleAddCompositesFunction(): RoleAddCompositesFunction = keycloakF2Function { cmd, client ->
        val composites = cmd.composites.map { roleId ->
            client.getRoleResource(cmd.realmId, roleId).toRepresentation()
        }
        client.getRoleResource(cmd.realmId, cmd.roleName).addComposites(composites)

        RoleAddCompositesResult(cmd.roleName)
    }
}
