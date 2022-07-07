pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "i2"

include(
    "i2-spring:i2-spring-boot-starter-auth",
    "i2-spring:i2-spring-boot-starter-auth-keycloak",
)
include(
    "i2-keycloak:keycloak-auth:keycloak-auth-client",
    "i2-keycloak:keycloak-auth:keycloak-auth-domain",
    "i2-keycloak:keycloak-utils"
)
include(
    "i2-keycloak:keycloak-f2:commons:commons-api",
    "i2-keycloak:keycloak-f2:commons:commons-domain"
)
include(
    "i2-keycloak:keycloak-f2:client:client-domain",
    "i2-keycloak:keycloak-f2:client:client-f2-create",
    "i2-keycloak:keycloak-f2:client:client-f2-generate-secret",
    "i2-keycloak:keycloak-f2:client:client-f2-query",
    "i2-keycloak:keycloak-f2:client:client-f2-update",
    "i2-keycloak:keycloak-f2:client:client-f2-roles-grant"
)
include(
    "i2-keycloak:keycloak-f2:group:group-domain",
    "i2-keycloak:keycloak-f2:group:group-f2-create",
    "i2-keycloak:keycloak-f2:group:group-f2-query",
    "i2-keycloak:keycloak-f2:group:group-f2-set-attributes",
    "i2-keycloak:keycloak-f2:group:group-f2-update"
)
include(
    "i2-keycloak:keycloak-f2:realm:realm-domain",
    "i2-keycloak:keycloak-f2:realm:realm-f2-create",
    "i2-keycloak:keycloak-f2:realm:realm-f2-query"
)
include(
    "i2-keycloak:keycloak-f2:role:role-domain",
    "i2-keycloak:keycloak-f2:role:role-f2-add-composites",
    "i2-keycloak:keycloak-f2:role:role-f2-create",
    "i2-keycloak:keycloak-f2:role:role-f2-query",
    "i2-keycloak:keycloak-f2:role:role-f2-update"
)
include(
    "i2-keycloak:keycloak-f2:user:user-domain",
    "i2-keycloak:keycloak-f2:user:user-f2-create",
    "i2-keycloak:keycloak-f2:user:user-f2-delete",
    "i2-keycloak:keycloak-f2:user:user-f2-disable",
    "i2-keycloak:keycloak-f2:user:user-f2-query",
    "i2-keycloak:keycloak-f2:user:user-f2-email-actions",
    "i2-keycloak:keycloak-f2:user:user-f2-join-group",
    "i2-keycloak:keycloak-f2:user:user-f2-roles-grant",
    "i2-keycloak:keycloak-f2:user:user-f2-roles-revoke",
    "i2-keycloak:keycloak-f2:user:user-f2-set-attributes",
    "i2-keycloak:keycloak-f2:user:user-f2-update",
    "i2-keycloak:keycloak-f2:user:user-f2-update-password"
)
include(
    "i2-test:test-bdd",
    "i2-test:test-it"
)
include(
    "i2-init:api-init",
    "i2-init:api-auth"
)
include(
    "i2-config:api-config",
    "i2-config:api-auth"
)
