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
        "i2-keycloak",
        "sample:spring-boot-auth-test"
)
include(
    "i2-keycloak:keycloak-auth:keycloak-auth-client",
    "i2-keycloak:keycloak-auth:keycloak-auth-domain"
)

include(
    "i2-s2:client:client-domain",
    "i2-s2:client:client-f2-create"
)

include(
    "i2-s2:realm:realm-domain",
    "i2-s2:realm:realm-f2-create"
)

include(
    "i2-s2:role:role-domain",
    "i2-s2:role:role-f2-create"
)

include(
    "i2-s2:user:user-domain",
    "i2-s2:user:user-f2-create",
    "i2-s2:user:user-f2-password-reset",
    "i2-s2:user:user-f2-roles-grant",
    "i2-s2:user:user-f2-roles-revoke"
)

include(
    "i2-test:test-bdd",
    "i2-test:test-it"
)