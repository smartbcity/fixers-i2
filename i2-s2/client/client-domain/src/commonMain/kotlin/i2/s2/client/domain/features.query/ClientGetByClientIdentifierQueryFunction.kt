package i2.s2.client.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientId
import i2.s2.client.domain.ClientIdentifier
import i2.s2.client.domain.ClientModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetByClientIdentifierQueryFunction = F2Function<ClientGetByClientIdentifierQuery, ClientGetByClientIdentifierQueryResult>
typealias ClientGetByClientIdentifierQueryRemoteFunction = F2FunctionRemote<ClientGetByClientIdentifierQuery, ClientGetByClientIdentifierQueryResult>

@JsExport
@JsName("ClientGetByClientIdentifierQuery")
class ClientGetByClientIdentifierQuery(
	val clientIdentifier: ClientIdentifier,
	val realmId: RealmId,
	val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientGetByClientIdentifierQueryResult")
class ClientGetByClientIdentifierQueryResult(
	val client: ClientModel?
): Event