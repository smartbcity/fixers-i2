package i2.init.api.init.keycloak

import i2.keycloak.master.domain.AuthRealmPassword
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class KeycloakAdminCliConfig {

    @Value("\${keycloak.auth-server-url}")
    lateinit var serverUrl: String

    @Value("\${keycloak.admin-cli.realm}")
    lateinit var realm: String

    @Value("\${keycloak.admin-cli.client-id}")
    lateinit var clientId: String

    @Value("\${keycloak.admin-cli.username}")
    lateinit var username: String

    @Value("\${keycloak.admin-cli.password}")
    lateinit var password: String

    @Bean
    @Primary
    fun adminCliAuthRealm() = AuthRealmPassword(
        serverUrl = serverUrl,
        realmId = realm,
        clientId = clientId,
        redirectUrl = "",
        username = username,
        password = password
    )
}
