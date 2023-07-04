package i2.init.api.config

import i2.keycloak.master.domain.AuthRealmPassword
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [KcInitProperties::class, I2KeycloakProperties::class])
class KcInitConfiguration {
    @Bean
    fun authRealm(properties: I2KeycloakProperties) = AuthRealmPassword(
        serverUrl = properties.authServerUrl,
        realmId = properties.realm,
        redirectUrl = "",
        clientId = properties.clientId,
        username = properties.username,
        password = properties.password
    )

}
