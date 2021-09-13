package i2.test.bdd.data.import

import i2.s2.client.domain.ClientIdentifier
import i2.s2.realm.domain.ClientImport
import i2.s2.role.domain.RoleName
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.clientImport(
    clientIdentifier: ClientIdentifier = UUID.randomUUID().toString(),
    isPublicClient: Boolean = true,
    isDirectAccessGrantsEnabled: Boolean = true,
    isServiceAccountsEnabled: Boolean = true,
    serviceAccountRoles: List<RoleName> = emptyList(),
    authorizationServicesEnabled: Boolean = false,
    rootUrl: String? = null,
    redirectUris: List<String> = emptyList(),
    baseUrl: String = "",
    adminUrl: String = "",
    webOrigins: List<String> = emptyList(),
    protocolMappers: Map<String, String> = emptyMap(),
) = ClientImport(
    clientIdentifier = clientIdentifier,
    isPublicClient = isPublicClient,
    isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
    isServiceAccountsEnabled = isServiceAccountsEnabled,
    serviceAccountRoles = serviceAccountRoles,
    authorizationServicesEnabled = authorizationServicesEnabled,
    rootUrl = rootUrl,
    redirectUris = redirectUris,
    baseUrl = baseUrl,
    adminUrl = adminUrl,
    webOrigins = webOrigins,
    protocolMappers = protocolMappers,
)
