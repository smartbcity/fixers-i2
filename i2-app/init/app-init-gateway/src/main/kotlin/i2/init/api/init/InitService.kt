package i2.init.api.init

import i2.init.api.init.keycloak.KeycloakInit
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class InitService(
    private val context: ConfigurableApplicationContext,
    private val keycloakInit: KeycloakInit
): CommandLineRunner {

    override fun run(vararg args: String?) {
        keycloakInit.init()
        context.close()
    }
}
