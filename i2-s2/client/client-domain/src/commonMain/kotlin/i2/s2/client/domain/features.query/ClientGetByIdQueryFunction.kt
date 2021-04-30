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

typealias ClientGetByIdQueryFunction = F2Function<ClientGetByIdQuery, ClientGetByIdQueryResult>
typealias ClientGetByIdQueryRemoteFunction = F2FunctionRemote<ClientGetByIdQuery, ClientGetByIdQueryResult>

@JsExport
@JsName("ClientGetByIdQuery")
class ClientGetByIdQuery(
	val id: ClientId,
	val realmId: RealmId,
	val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientGetByIdQueryResult")
class ClientGetByIdQueryResult(
	val client: ClientModel?
): Event