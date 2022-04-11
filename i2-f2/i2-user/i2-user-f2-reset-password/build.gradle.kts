plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-f2:i2-user:i2-user-domain"))

    implementation(project(":i2-f2:config"))
    api(project(":i2-keycloak:keycloak-f2:user:user-f2-reset-password"))
}
