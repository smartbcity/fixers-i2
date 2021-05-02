package i2.s2.client.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientId
import i2.s2.client.domain.ClientIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientCreateFunction = F2Function<ClientCreateCommand, ClientCreatedResult>
typealias ClientCreateRemoteFunction = F2FunctionRemote<ClientCreateCommand, ClientCreatedResult>

@JsExport
@JsName("ClientCreateCommand")
class ClientCreateCommand(
	val clientIdentifier: ClientIdentifier,
	val realmId: RealmId,
	val auth: AuthRealm,
	val isPublicClient: Boolean = true,
	val isDirectAccessGrantsEnabled: Boolean = true,
	val rootUrl: String? = null,
	val redirectUris: List<String> = emptyList(),
	val baseUrl: String = "",
	val adminUrl: String = "",
	val webOrigins: List<String> = emptyList(),
	val protocolMappers: List<String> = emptyList(),
) : Command

@JsExport
@JsName("ClientCreatedResult")
class ClientCreatedResult(
	val id: String
) : Event