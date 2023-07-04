package i2.init.api.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("i2.keycloak")
class I2KeycloakProperties(
    val authServerUrl: String,
    val realm: String,
    val clientId: String,
    val username: String,
    val password: String,
)
