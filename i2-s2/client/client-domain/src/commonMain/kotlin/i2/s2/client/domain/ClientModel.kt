package i2.s2.client.domain

import kotlin.js.JsExport
import kotlin.js.JsName


typealias ClientId = String
typealias ClientIdentifier = String

@JsExport
@JsName("ClientModel")
class ClientModel(
	val id: ClientId,
	val clientIdentifier: ClientIdentifier
)
