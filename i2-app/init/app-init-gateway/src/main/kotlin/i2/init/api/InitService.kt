package i2.init.api

import i2.init.api.auth.KeycloakInitProperties
import i2.init.api.auth.KeycloakInitService
import i2.init.api.config.KcInitProperties
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class InitService(
    private val context: ConfigurableApplicationContext,
    private val keycloakInitService: KeycloakInitService,
    private val keycloakInitProperties: KeycloakInitProperties,
    private val i2Initproperties: KcInitProperties
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(InitService::class.java)

    override fun run(vararg args: String?) = runBlocking {
        val success = retryWithExceptions(i2Initproperties.maxRetries, i2Initproperties.retryDelayMillis, log) {
            keycloakInitService.init(keycloakInitProperties)
        }

        if (!success) {
            log.error("Could not initialize Keycloak. Exiting application.")
            // Handle the situation when initialization failed after all attempts
        }

        context.close()
    }
}
