package i2.init.api.init.keycloak

import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
data class KeycloakUserConfig(
    val username: String,
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val role: String
)
