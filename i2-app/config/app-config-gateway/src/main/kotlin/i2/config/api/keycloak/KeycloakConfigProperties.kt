package i2.config.api.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "i2")
class KeycloakConfigProperties(
    var configPath: String
)
