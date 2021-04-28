package i2.s2.client.domain.features.query

import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetSecretQueryFunction = F2Function<ClientGetSecretQuery, ClientGetSecretQueryResult>
typealias ClientGetSecretQueryFunctionRemote = F2FunctionRemote<ClientGetSecretQuery, ClientGetSecretQueryResult>

@JsExport
@JsName("ClientGetSecretQuery")
class ClientGetSecretQuery(
    val clientId: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm
)

@JsExport
@JsName("ClientGetSecretQueryResult")
class ClientGetSecretQueryResult(
    val secret: String
)