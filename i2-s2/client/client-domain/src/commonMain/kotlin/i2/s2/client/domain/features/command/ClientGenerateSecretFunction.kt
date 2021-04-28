package i2.s2.client.domain.features.command

import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.s2.client.domain.ClientId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGenerateSecretFunction = F2Function<ClientGenerateSecretCommand, ClientGenerateSecretResult>
typealias ClientGenerateSecretFunctionRemote = F2FunctionRemote<ClientGenerateSecretCommand, ClientGenerateSecretResult>

@JsExport
@JsName("ClientGenerateSecretCommand")
class ClientGenerateSecretCommand(
    val id: ClientId,
    val realmId: String,
    val auth: AuthRealm
)

@JsExport
@JsName("ClientGenerateSecretResult")
class ClientGenerateSecretResult(
    val secret: String
)