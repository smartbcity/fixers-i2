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
    override val clientId: String,
    override val roles: Array<String>?,
    override val realmManagementRoles: Array<String>?,
    override val secret: String?,
    val webUrl: String,
    val localhostUrl: String?
): AppClient(clientId = clientId, secret = secret, roles = roles, realmManagementRoles = realmManagementRoles)

open class AppClient (
    open val clientId: String,
    open val secret: String?,
    open val roles: Array<String>?,
    open val realmManagementRoles: Array<String>?
)
