plugins {
    kotlin("multiplatform")
}

dependencies {

    commonMainApi(project(":i2-keycloak:keycloak-auth:keycloak-auth-domain"))
    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
