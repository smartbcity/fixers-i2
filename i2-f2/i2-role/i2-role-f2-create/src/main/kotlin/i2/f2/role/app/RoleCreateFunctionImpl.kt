package i2.f2.role.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.role.domain.features.command.RoleCreateCommand
import i2.f2.role.domain.features.command.RoleCreateFunction
import i2.f2.role.domain.features.command.RoleCreatedResult
import i2.keycloak.master.domain.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakRoleCreateCommand = i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
private typealias KeycloakRoleCreateFunction = i2.keycloak.f2.role.domain.features.command.RoleCreateFunction

@Configuration
class RoleCreateFunctionImpl(
    private val authRealm: AuthRealm,
    private val i2KeycloakConfig: I2KeycloakConfig,
    private val keycloakRoleCreateFunction: KeycloakRoleCreateFunction
) {

    @Bean
    fun i2RoleCreateFunction(): RoleCreateFunction = f2Function { cmd ->
        val roleId = keycloakRoleCreateFunction.invoke(cmd.toKeycloakRoleCreateCommand()).id
        RoleCreatedResult(roleId)
    }

    private fun RoleCreateCommand.toKeycloakRoleCreateCommand() = KeycloakRoleCreateCommand(
        name = this.name,
        composites = this.composites,
        description = this.description,
        isClientRole = this.isClientRole,
        realmId = i2KeycloakConfig.realm,
        auth = authRealm
    )
}
