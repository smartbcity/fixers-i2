pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
}

rootProject.name = "i2"

enableFeaturePreview("GRADLE_METADATA")

include(
        "i2-spring:i2-spring-boot-starter-auth",
        "auth-test"
)

include(
    "i2-keycloak:keycloak-master:keycloak-master-client",
    "i2-keycloak:keycloak-master:keycloak-master-domain",
    "i2-keycloak:keycloak-realm:keycloak-realm-client",
    "i2-keycloak:keycloak-realm:keycloak-realm-domain"
)

include(
    "i2-s2:realm:realm-domain",
    "i2-s2:realm:realm-f2-create"
)

include(
    "i2-s2:user:user-domain",
    "i2-s2:user:user-f2-create",
    "i2-s2:user:user-f2-reset-password"
)