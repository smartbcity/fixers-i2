package i2.commons.http

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.parametersOf

open class Client(
    private val baseUrl: String,
    httpClientBuilder: ClientBuilder,
    protected open var generateBearerToken: suspend () -> String? = { null },
) {
    protected val client = httpClientBuilder.build()

    protected suspend inline fun <reified T> get(path: String, withAuth: Boolean = true): T {
        return client.get {
            basicSetup(path, withAuth)
        }
    }

    protected suspend inline fun <reified T> post(path: String, jsonBody: Any, withAuth: Boolean = true): T {
        return client.post {
            jsonSetup(path, jsonBody, withAuth)
        }
    }

    protected suspend inline fun <reified T> post(path: String, formData: Map<String, String>, withAuth: Boolean = true): T {
        return client.post {
            formDataSetup(path, formData, withAuth)
        }
    }

    protected suspend inline fun <reified T> put(path: String, jsonBody: Any, withAuth: Boolean = true): T {
        return client.put {
            jsonSetup(path, jsonBody, withAuth)
        }
    }

    protected suspend inline fun <reified T> put(path: String, formData: Map<String, String>, withAuth: Boolean = true): T {
        return client.put {
            formDataSetup(path, formData, withAuth)
        }
    }

    protected suspend fun HttpRequestBuilder.jsonSetup(path: String, jsonBody: Any, withAuth: Boolean) {
        basicSetup(path, withAuth)
        header("Content-Type", ContentType.Application.Json)
        this.body = jsonBody
    }

    protected suspend fun HttpRequestBuilder.formDataSetup(path: String, formData: Map<String, String>, withAuth: Boolean) {
        basicSetup(path, withAuth)
        val parameters = formData.map { (key, value) -> key to listOf(value) }
                .toTypedArray()

        this.body = FormDataContent(parametersOf(*parameters))
    }

    protected suspend fun HttpRequestBuilder.basicSetup(path: String, withAuth: Boolean) {
        if (withAuth) {
            generateBearerToken()?.let { token ->
                header("Authorization", "Bearer $token")
            }
        }
        println("$baseUrl/$path")
        url("$baseUrl/$path")
    }
}
