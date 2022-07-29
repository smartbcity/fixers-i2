package city.smartb.i2.spring.boot.auth.keycloak

import city.smartb.i2.spring.boot.auth.config.I2TrustedIssuerProperties

class I2KeycloakIssuers(
    name: String,
    authUrl: String,
    realm: String,
    val web: I2KeycloakProperties
): I2TrustedIssuerProperties(
    name = name,
    authUrl = authUrl,
    realm = realm
)

class I2KeycloakProperties(
    val clientId: String
)
