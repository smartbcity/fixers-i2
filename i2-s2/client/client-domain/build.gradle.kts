plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":i2-keycloak:keycloak-auth:keycloak-auth-domain"))
    commonMainApi(project(":i2-s2:error:error-domain"))
    commonMainApi(project(":i2-s2:role:role-domain"))

    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}