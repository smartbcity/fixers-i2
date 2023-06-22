package i2.config.api.keycloak

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig {

    @Value("\${i2.json}")
    lateinit var configPath: String
}
