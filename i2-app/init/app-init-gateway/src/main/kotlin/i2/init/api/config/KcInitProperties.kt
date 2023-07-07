package i2.init.api.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("i2.init")
class KcInitProperties(
    val maxRetries: Int,
    val retryDelayMillis: Long,
    val realm: String,
    val smtp: Map<String, String>,
    val adminClient: KcInitClientProperties?,
    val baseRoles: List<String>,
    val adminUser: KcInitUserProperties?
)

data class KcInitUserProperties(
        val username: String? = null,
        val password: String? = null,
        val email: String? = null,
        val firstname: String? = null,
        val lastname: String? = null,
)

data class KcInitClientProperties(
        val name: String,
        val secret: String? = null,
)
