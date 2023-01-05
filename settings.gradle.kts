pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        mavenLocal()
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
    "i2-keycloak:keycloak-f2:commons:i2-commons-api",
    "i2-keycloak:keycloak-f2:commons:i2-commons-domain"
)
include(
    "i2-keycloak:keycloak-f2:client:i2-client-domain",
    "i2-keycloak:keycloak-f2:client:i2-client-f2-command",
    "i2-keycloak:keycloak-f2:client:i2-client-f2-query",
)
include(
    "i2-keycloak:keycloak-f2:group:i2-group-domain",
    "i2-keycloak:keycloak-f2:group:i2-group-f2-command",
    "i2-keycloak:keycloak-f2:group:i2-group-f2-query"
)
include(
    "i2-keycloak:keycloak-f2:realm:i2-realm-domain",
    "i2-keycloak:keycloak-f2:realm:i2-realm-f2-create",
    "i2-keycloak:keycloak-f2:realm:i2-realm-f2-query"
)
include(
    "i2-keycloak:keycloak-f2:role:i2-role-domain",
    "i2-keycloak:keycloak-f2:role:i2-role-f2-add-composites",
    "i2-keycloak:keycloak-f2:role:i2-role-f2-create",
    "i2-keycloak:keycloak-f2:role:i2-role-f2-query",
    "i2-keycloak:keycloak-f2:role:i2-role-f2-update"
)
include(
    "i2-keycloak:keycloak-f2:user:i2-user-domain",
    "i2-keycloak:keycloak-f2:user:i2-user-f2-command",
    "i2-keycloak:keycloak-f2:user:i2-user-f2-query"
)
include(
    "i2-keycloak:keycloak-plugin",
    "i2-keycloak:keycloak-plugin:keycloak-generate-action-token",
    "i2-keycloak:keycloak-plugin:keycloak-event-listener-http",
    "i2-keycloak:keycloak-plugin:keycloak-plugin-client",
    "i2-keycloak:keycloak-plugin:keycloak-plugin-domain",
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
