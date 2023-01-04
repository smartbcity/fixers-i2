package city.smartb.i2.spring.boot.auth.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "i2")
data class I2KeycloakConfig (
    val issuers: List<I2KeycloakIssuers> = emptyList()
) {
    fun getConfig(): Map<String, KeycloakConfig> {
        return issuers.associate {
            it.name to KeycloakConfig(
                realm = it.realm,
                authServerUrl = it.authUrl,
                resource = it.web?.clientId
            )
        }
    }
}
