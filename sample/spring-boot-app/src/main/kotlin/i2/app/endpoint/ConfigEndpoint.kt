package i2.app.endpoint

import i2.app.config.KeycloakConfigDTO
import i2.f2.config.I2KeycloakConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.security.PermitAll

@Configuration
class ConfigEndpoint(
    private val keycloakConfig: I2KeycloakConfig
) {
    @PermitAll
    @Bean
    fun keycloak(): () -> KeycloakConfigDTO = {
        KeycloakConfigDTO(
            realm = keycloakConfig.realm,
            authServerUrl = keycloakConfig.authServerUrl,
            resource = keycloakConfig.authRealm().clientId,
        )
    }
}
