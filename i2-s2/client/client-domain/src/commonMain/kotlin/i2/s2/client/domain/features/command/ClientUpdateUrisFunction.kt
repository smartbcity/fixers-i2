package i2.s2.client.domain.features.command

import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientUpdateUrisFunction = F2Function<ClientUpdateUrisCommand, ClientUpdateUrisResult>
typealias ClientUpdateUrisFunctionRemote = F2FunctionRemote<ClientUpdateUrisCommand, ClientUpdateUrisResult>

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
@JsName("ClientUpdateUrisResult")
class ClientUpdateUrisResult(
    val id: ClientId
)