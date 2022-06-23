package city.smartb.i2.spring.boot.auth.keycloak

import org.springframework.stereotype.Service

@Service
class I2KeycloakConfigResolver(
    private val i2KeycloakConfig: I2KeycloakConfig
) {
    suspend fun getKeycloakConfig(name: String?): KeycloakConfigDTO {
        if (name.isNullOrBlank()) {
            return i2KeycloakConfig.getConfig().values.first()
        }

        return i2KeycloakConfig.getConfig()[name]
            ?: throw NullPointerException("No config found for this name $name")
    }
}
