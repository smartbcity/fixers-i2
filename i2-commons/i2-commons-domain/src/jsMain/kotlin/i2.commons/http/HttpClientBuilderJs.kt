package i2.commons.http

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature

class HttpClientBuilderJs: ClientBuilder {
	override fun build(): HttpClient {
		return HttpClient {
			install(JsonFeature)
		}
	}
}
