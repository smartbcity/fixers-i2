package city.smartb.i2.spring.boot.auth.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "i2")
@ConstructorBinding
data class I2KeycloakConfig (
    val issuers: List<I2KeycloakProperties>
) {
    fun getConfig(): Map<String, KeycloakConfigDTO> {
        if (issuers.isNullOrEmpty()) {
            return emptyMap()
        }

        return issuers.associate {
            it.name to KeycloakConfigDTO(
                realm = it.realm,
                authServerUrl = it.authUrl,
                resource = it.clientId
            )
        }
    }
}
