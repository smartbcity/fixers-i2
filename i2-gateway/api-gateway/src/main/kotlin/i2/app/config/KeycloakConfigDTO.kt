package i2.app.config

import com.fasterxml.jackson.annotation.JsonProperty

data class KeycloakConfigDTO(
    val realm: String,
    @JsonProperty("auth-server-url")
    val authServerUrl: String,
    val resource: String
)
