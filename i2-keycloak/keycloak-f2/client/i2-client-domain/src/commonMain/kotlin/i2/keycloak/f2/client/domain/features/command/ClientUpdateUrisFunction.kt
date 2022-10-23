package i2.keycloak.f2.client.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientUpdateUrisFunction = F2Function<ClientUpdateUrisCommand, ClientUpdatedUrisEvent>

@JsExport
@JsName("ClientUpdateUrisCommand")
class ClientUpdateUrisCommand(
    val id: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm,
    val rootUrl: String,
    val redirectUris: List<String>,
    val baseUrl: String
)

@JsExport
@JsName("ClientUpdatedUrisEvent")
class ClientUpdatedUrisEvent(
    val id: ClientId
)
