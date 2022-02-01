plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-f2:organization:organization-domain"))

    implementation(project(":i2-keycloak:keycloak-f2:group:group-f2-update"))
}
