package i2.config.api.auth

import i2.keycloak.f2.client.domain.ClientIdentifier

class KeycloakConfigProperties (
    val users: List<KeycloakUserConfig>?,
    val roles: List<String>?,
    val roleComposites: Map<String, List<String>>?,
    val webClients: List<WebClient>,
    val appClients: List<AppClient>
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
    val clientId: ClientIdentifier,
    val webUrl: String
)

class AppClient (
    val clientId: ClientIdentifier,
    val clientSecret: String?,
    val roles: Array<String>?,
    val realmManagementRoles: Array<String>?
)
