pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "i2"

include(
    "i2-commons:i2-commons-api",
    "i2-commons:i2-commons-domain",
)
include(
    "i2-spring:i2-spring-boot-starter-auth",
    "i2-keycloak:keycloak-f2",
    "sample:spring-boot-app"
)
include(
    "i2-f2:import:import-domain",
    "i2-f2:import:import-data"
)
include(
    "i2-f2:organization:organization-domain",
    "i2-f2:organization:organization-f2-create",
    "i2-f2:organization:organization-f2-query",
    "i2-f2:organization:organization-f2-update"
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
    "i2-keycloak:keycloak-f2:client:client-f2-update"
)
include(
    "i2-keycloak:keycloak-f2:group:group-domain",
    "i2-keycloak:keycloak-f2:group:group-f2-create",
    "i2-keycloak:keycloak-f2:group:group-f2-query",
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
    "i2-keycloak:keycloak-f2:role:role-f2-query"
)
include(
    "i2-keycloak:keycloak-f2:user:user-domain",
    "i2-keycloak:keycloak-f2:user:user-f2-create",
    "i2-keycloak:keycloak-f2:user:user-f2-delete",
    "i2-keycloak:keycloak-f2:user:user-f2-disable",
    "i2-keycloak:keycloak-f2:user:user-f2-query",
    "i2-keycloak:keycloak-f2:user:user-f2-password-reset",
    "i2-keycloak:keycloak-f2:user:user-f2-roles-grant",
    "i2-keycloak:keycloak-f2:user:user-f2-roles-revoke",
    "i2-keycloak:keycloak-f2:user:user-f2-update"
)
include(
    "i2-test:test-bdd",
    "i2-test:test-it"
)
