package i2.f2.role.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.role.domain.features.command.RoleUpdateCommand
import i2.f2.role.domain.features.command.RoleUpdateFunction
import i2.f2.role.domain.features.command.RoleUpdatedResult
import i2.keycloak.master.domain.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakRoleUpdateCommand = i2.keycloak.f2.role.domain.features.command.RoleUpdateCommand
private typealias KeycloakRoleUpdateFunction = i2.keycloak.f2.role.domain.features.command.RoleUpdateFunction

@Configuration
class RoleUpdateFunctionImpl(
    private val authRealm: AuthRealm,
    private val i2KeycloakConfig: I2KeycloakConfig,
    private val keycloakRoleUpdateFunction: KeycloakRoleUpdateFunction
) {

    @Bean
    fun i2RoleUpdateFunction(): RoleUpdateFunction = f2Function { cmd ->
        val roleId = keycloakRoleUpdateFunction.invoke(cmd.toKeycloakRoleUpdateCommand()).id
        RoleUpdatedResult(roleId)
    }

    private fun RoleUpdateCommand.toKeycloakRoleUpdateCommand() = KeycloakRoleUpdateCommand(
        name = this.name,
        composites = this.composites,
        description = this.description,
        isClientRole = this.isClientRole,
        realmId = i2KeycloakConfig.realm,
        auth = authRealm
    )
}