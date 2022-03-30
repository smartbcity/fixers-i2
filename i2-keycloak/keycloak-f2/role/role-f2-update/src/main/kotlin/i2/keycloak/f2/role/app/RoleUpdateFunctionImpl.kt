package i2.keycloak.f2.role.app

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.role.domain.features.command.RoleUpdateFunction
import i2.keycloak.f2.role.domain.features.command.RoleUpdatedResult
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleUpdateFunctionImpl {

    @Bean
    fun roleUpdateFunction(): RoleUpdateFunction = keycloakF2Function { cmd, client ->
        try {
            val roleResource = client.getRoleResource(cmd.realmId, cmd.name)

            val newComposites = cmd.composites.map { role ->
                client.getRoleResource(cmd.realmId, role).toRepresentation()
            }

            roleResource.deleteComposites(roleResource.roleComposites.toList())
            roleResource.addComposites(newComposites)

            val roleRepresentation = roleResource.toRepresentation().apply {
                description = cmd.description
                clientRole = cmd.isClientRole
            }

            roleResource.update(roleRepresentation)

            RoleUpdatedResult(cmd.name)
        } catch (e: Exception) {
            throw I2ApiError(
                description = "Realm[${cmd.realmId}] Role[${cmd.name}] Error updating",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
