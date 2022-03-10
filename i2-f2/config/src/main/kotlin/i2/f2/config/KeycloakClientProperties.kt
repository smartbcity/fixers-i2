package i2.f2.config

import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
data class KeycloakClientProperties(
    val clientId: String,
    val realm: String?,
    val clientSecret: String?,
    val username: String?,
    val password: String?,
)
