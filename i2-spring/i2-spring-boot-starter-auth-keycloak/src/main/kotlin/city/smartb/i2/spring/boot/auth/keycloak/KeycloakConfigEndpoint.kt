package city.smartb.i2.spring.boot.auth.keycloak

import javax.annotation.security.PermitAll
import kotlinx.coroutines.runBlocking
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(I2KeycloakConfig::class)
class KeycloakConfigEndpoint(
    private val i2KeycloakConfigResolver: I2KeycloakConfigResolver
) {

    @PermitAll
    @Bean
    fun keycloak(): (name: String) -> KeycloakConfigDTO = { name ->
        runBlocking {
            val keycloakConfig = i2KeycloakConfigResolver.getKeycloakConfig(name)

            KeycloakConfigDTO(
                realm = keycloakConfig.realm,
                authServerUrl = keycloakConfig.authServerUrl,
                resource = keycloakConfig.resource,
            )
        }
    }
}
