package i2.init.api.keycloak

import i2.init.api.auth.KeycloakInitProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

data class KcInitUserProperties(
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
)

data class KcInitClientProperties(
    val clientId: String? = null,
    val clientSecret: String? = null,
)

data class KcInitProperties(
    val smtpConfig: Map<String, String>,
    val baseRoles: List<String>,
    val realm: String,
    val adminClient: KcInitClientProperties,
    val adminUser: KcInitUserProperties,

)


