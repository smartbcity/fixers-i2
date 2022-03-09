package i2.init.api.auth.config

import i2.keycloak.master.domain.AuthRealmPassword
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig {

    @Value("\${keycloak.auth-server-url}")
    lateinit var serverUrl: String

    @Value("\${keycloak.realm}")
    lateinit var realm: String

    @Value("\${keycloak.web.client-id}")
    lateinit var webClientId: String

    @Value("\${keycloak.app.client-id}")
    lateinit var appClientId: String

    @Value("\${keycloak.admin-cli.realm}")
    lateinit var adminRealm: String

    @Value("\${keycloak.admin-cli.client-id}")
    lateinit var adminClientId: String

    @Value("\${keycloak.admin-cli.username}")
    lateinit var adminUsername: String

    @Value("\${keycloak.admin-cli.password}")
    lateinit var adminPassword: String

    @Bean
    fun authRealm() = AuthRealmPassword(
        serverUrl = serverUrl,
        realmId = adminRealm,
        redirectUrl = "",
        clientId = adminClientId,
        username = adminUsername,
        password = adminPassword
    )
}
