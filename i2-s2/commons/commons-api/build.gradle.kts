plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-s2:commons:commons-domain"))
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))
    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")
}
