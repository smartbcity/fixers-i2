package i2.config.api.config

import i2.config.api.config.keycloak.KeycloakConfig
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class ConfigService(
    private val context: ConfigurableApplicationContext,
    private val keycloakConfig: KeycloakConfig
): CommandLineRunner {

    override fun run(vararg args: String?) {
        keycloakConfig.run()
        context.close()
    }
}
