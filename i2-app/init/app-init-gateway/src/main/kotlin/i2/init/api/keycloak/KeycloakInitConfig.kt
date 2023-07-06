package i2.init.api.keycloak

import i2.init.api.auth.KeycloakInitProperties
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

    @Value("\${i2.init.admin-client.name}")
    lateinit var clientId: String

    @Value("\${i2.init.admin-client.secret:#{null}}")
    var clientSecret: String? = null

    @Value("\${i2.init.admin-user.username:#{null}}")
    var username: String? = null

    @Value("\${i2.init.admin-user.password:#{null}}")
    var password: String? = null

    @Value("\${i2.init.admin-user.email:#{null}}")
    var email: String? = null

    @Value("\${i2.init.admin-user.firstname:#{null}}")
    var firstname: String? = null

    @Value("\${i2.init.admin-user.lastname:#{null}}")
    var lastname: String? = null

    @Bean
    @ConfigurationProperties(prefix = "i2.init.base-roles")
    fun baseRoles(): List<String> = mutableListOf()

    @Bean
    fun keycloakInitProperties(): KeycloakInitProperties {
        return KeycloakInitProperties(
            smtpConfig = smtpConfig(),
            realm = realm,
            clientId = clientId,
            clientSecret = clientSecret,
            username = username,
            password = password,
            email = email,
            firstname = firstname,
            lastname = lastname,
            baseRoles = baseRoles()
        )
    }
}
