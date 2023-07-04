package i2.app.core

import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("i2.keycloak")
data class KcAuthProperties(
    val serverUrl: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String,
)

abstract class KcAuthConfiguration {

    @Bean
    fun authRealm(properties: KcAuthProperties) = AuthRealmClientSecret(
        serverUrl = properties.serverUrl,
        realmId = properties.realm,
        clientId = properties.clientId,
        clientSecret = properties.clientSecret,
        redirectUrl = null
    )
}
