package i2.init.api.init.keycloak

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakInitConfig {

    @Bean
    @ConfigurationProperties(prefix = "i2.init.smtp")
    fun smtpConfig(): Map<String, String> = mutableMapOf()

    @Value("\${i2.init.realm}")
    lateinit var realm: String

    @Value("\${i2.init.admin-client}")
    lateinit var clientId: String

    @Value("\${i2.init.admin-user.username}")
    lateinit var username: String

    @Value("\${i2.init.admin-user.email}")
    lateinit var email: String

    @Value("\${i2.init.admin-user.firstname}")
    lateinit var firstname: String

    @Value("\${i2.init.admin-user.lastname}")
    lateinit var lastname: String

    @Bean
    @ConfigurationProperties(prefix = "i2.init.base-roles")
    fun baseRoles(): List<String> = mutableListOf()
}
