plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-s2:user:user-domain"))
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")

    testImplementation(project(":i2-test:test-bdd"))
    testImplementation(project(":i2-s2:user:user-f2-create"))
}
