plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-f2:i2-role:i2-role-domain"))

    implementation(project(":i2-f2:config"))

    api(project(":i2-keycloak:keycloak-f2:role:role-f2-add-composites"))
}
