package i2.commons.http

import io.ktor.client.HttpClient
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("ClientBuilder")
interface ClientBuilder {
	fun build(): HttpClient
}
