package i2.config.api

import i2.config.api.auth.KeycloakConfigService
import i2.config.api.keycloak.KeycloakConfig
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class ConfigService(
    private val context: ConfigurableApplicationContext,
    private val keycloakConfigService: KeycloakConfigService,
    private val keycloakConfig: KeycloakConfig
) : CommandLineRunner {
    override fun run(vararg args: String?) = runBlocking {
        keycloakConfigService.run(keycloakConfig.configPath)
        context.close()
    }
}
