package i2.init.api.keycloak

data class KeycloakUserConfig(
    val username: String,
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val role: String
)