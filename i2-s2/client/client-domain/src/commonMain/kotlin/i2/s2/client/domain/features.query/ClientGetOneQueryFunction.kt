package i2.s2.client.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientId
import i2.s2.client.domain.ClientModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetOneQueryFunction = F2Function<ClientGetOneQuery, ClientGetOneQueryResult>
typealias ClientGetOneQueryRemoteFunction = F2FunctionRemote<ClientGetOneQuery, ClientGetOneQueryResult>

@JsExport
@JsName("ClientGetOneQuery")
class ClientGetOneQuery(
	val id: ClientId,
	val realmId: RealmId,
	val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientGetOneQueryResult")
class ClientGetOneQueryResult(
	val client: ClientModel?
): Event