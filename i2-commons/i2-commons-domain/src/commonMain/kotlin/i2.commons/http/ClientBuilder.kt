package i2.commons.http

import io.ktor.client.HttpClient

interface ClientBuilder {
	fun build(): HttpClient
}
