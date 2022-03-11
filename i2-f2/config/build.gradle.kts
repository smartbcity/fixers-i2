plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-commons:i2-commons-api"))
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-domain"))
}