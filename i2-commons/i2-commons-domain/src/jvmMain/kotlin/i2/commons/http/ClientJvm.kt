package i2.commons.http

abstract class ClientJvm(
    baseUrl: String,
    generateBearerToken: suspend () -> String? = { null },
): Client(
    baseUrl = baseUrl,
    httpClientBuilder = HttpClientBuilderJvm,
    generateBearerToken = generateBearerToken
)
