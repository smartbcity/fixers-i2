plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":i2-keycloak:keycloak-auth:keycloak-auth-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:commons:commons-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:client:client-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:realm:realm-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:role:role-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:user:user-domain"))

    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
