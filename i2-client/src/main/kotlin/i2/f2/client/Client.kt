package i2.f2.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.serialization.jackson.jackson

open class Client(
    protected val baseUrl: String
) {
    protected val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    protected suspend inline fun <reified T> post(path: String, jsonBody: Any): T {
        return httpClient.post {
            url("$baseUrl/$path")
            header("Content-Type", ContentType.Application.Json)
            setBody(jsonBody)
        }.body()
    }

    protected suspend inline fun <reified T> post(path: String, jsonBody: Any, token: String): T {
        return httpClient.post {
            url("$baseUrl/$path")
            header("Content-Type", ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            setBody(jsonBody)
        }.body()
    }
}
