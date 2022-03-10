package i2.commons.http

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature

object HttpClientBuilderJvm: ClientBuilder {
	override fun build(): HttpClient {
		return HttpClient(CIO) {
			install(JsonFeature) {
				serializer = JacksonSerializer {
					this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
							.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
							.registerModule(KotlinModule())
							.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
				}
			}
		}
	}
}
