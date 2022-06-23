package city.smartb.i2.spring.boot.auth.keycloak

data class KeycloakConfigDTO(
    val realm: String,
    val authServerUrl: String,
    val resource: String
)
