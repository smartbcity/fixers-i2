package i2.init.api.keycloak

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


