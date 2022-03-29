package i2.f2.role.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.role.domain.features.command.RoleAddCompositesCommand
import i2.f2.role.domain.features.command.RoleAddCompositesFunction
import i2.f2.role.domain.features.command.RoleAddCompositesResult
import i2.keycloak.master.domain.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakRoleAddCompositesCommand = i2.keycloak.f2.role.domain.features.command.RoleAddCompositesCommand
private typealias KeycloakRoleAddCompositesFunction = i2.keycloak.f2.role.domain.features.command.RoleAddCompositesFunction

@Configuration
class RoleAddCompositesFunctionImpl(
    private val authRealm: AuthRealm,
    private val i2KeycloakConfig: I2KeycloakConfig,
    private val keycloakRoleAddCompositesFunction: KeycloakRoleAddCompositesFunction
) {

    @Bean
    fun i2RoleAddCompositesFunction(): RoleAddCompositesFunction = f2Function { cmd ->
        val roleName = keycloakRoleAddCompositesFunction.invoke(cmd.toKeycloakRoleAddCompositesCommand()).id
        RoleAddCompositesResult(roleName)
    }

    private fun RoleAddCompositesCommand.toKeycloakRoleAddCompositesCommand() = KeycloakRoleAddCompositesCommand(
        roleName = this.roleName,
        composites = this.composites,
        realmId = i2KeycloakConfig.realm,
        auth = authRealm
    )
}