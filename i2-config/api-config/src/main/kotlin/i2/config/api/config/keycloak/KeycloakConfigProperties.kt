package i2.config.api.config.keycloak

class KeycloakConfigProperties (
    val users: List<KeycloakUserConfig>?,
    val roles: List<String>?,
    val roleComposites: Map<String, List<String>>?,
    val webClient: WebClient,
    val appClient: AppClient
)

class KeycloakUserConfig(
    val username: String,
    val email: String,
    val password: String?,
    val firstname: String,
    val lastname: String,
    val role: String
)

class WebClient (
    val clientId: String,
    val webUrl: String,
    val localhostUrl: String?
)

class AppClient (
    val clientId: String
)
