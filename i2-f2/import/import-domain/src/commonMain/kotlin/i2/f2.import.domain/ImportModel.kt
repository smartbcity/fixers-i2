package i2.f2.import.domain

import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.role.domain.RoleName

class RealmImport(
	val id: String,
	val theme: String?,
	val locale: String?,
	val smtpServer: Map<String, String>?,
	val roles: List<RoleImport>,
	val clients: List<ClientImport>,
	val users: List<UserImport>
)

class ClientImport(
	val clientIdentifier: ClientIdentifier,
	val isPublicClient: Boolean = true,
	val isDirectAccessGrantsEnabled: Boolean = true,
	val isServiceAccountsEnabled: Boolean = true,
	val serviceAccountRoles: List<RoleName> = emptyList(),
	val authorizationServicesEnabled: Boolean = false,
	val rootUrl: String? = null,
	val redirectUris: List<String> = emptyList(),
	val baseUrl: String = "",
	val adminUrl: String = "",
	val webOrigins: List<String> = emptyList(),
	val protocolMappers: Map<String, String> = emptyMap(),
)

class RoleImport(
	val name: RoleName,
	val description: String?,
	val isClientRole: Boolean,
	val composites: List<RoleName>,
)

class UserImport(
	val username: String,
	val firstname: String?,
	val lastname: String?,
	val email: String,
	val isEnable: Boolean,
	val metadata: Map<String, String>,
	val roles: List<RoleName>
)
