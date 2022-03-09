package i2.init.api.init.keycloak

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakInitConfig {

    @Bean
    @ConfigurationProperties(prefix = "keycloak.smtp")
    fun smtpConfig(): Map<String, String> = mutableMapOf()

    @Bean
    @ConfigurationProperties(prefix = "keycloak.users")
    fun users(): List<KeycloakUserConfig> = mutableListOf()

    @Bean
    @ConfigurationProperties(prefix = "keycloak.roles")
    fun roles(): List<String> = mutableListOf()

    @Bean
    @ConfigurationProperties(prefix = "keycloak.role-composites")
    fun roleComposites(): Map<String, List<String>> = mutableMapOf()

    @Value("\${keycloak.web.web-url}")
    lateinit var webUrl: String

    @Value("\${keycloak.web.localhost-url}")
    lateinit var localhostUrl: String
}
