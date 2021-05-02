package i2.test.bdd.data.client

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientId
import i2.s2.client.domain.ClientIdentifier
import i2.s2.client.domain.features.command.ClientCreateCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.Companion.clientCreateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    clientIdentifier: ClientIdentifier = UUID.randomUUID().toString(),
    isPublicClient: Boolean = true,
    isDirectAccessGrantsEnabled: Boolean = true,
    rootUrl: String? = null,
    redirectUris: List<String> = emptyList(),
    baseUrl: String = "",
    adminUrl: String = "",
    webOrigins: List<String> = emptyList(),
    protocolMappers: List<String> = emptyList(),
) = ClientCreateCommand(
    clientIdentifier = clientIdentifier,
    realmId = realmId,
    isPublicClient = isPublicClient,
    isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
    rootUrl = rootUrl,
    redirectUris = redirectUris,
    baseUrl = baseUrl,
    adminUrl = adminUrl,
    webOrigins = webOrigins,
    protocolMappers = protocolMappers,
    auth = auth
)