plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-s2:organization:organization-domain"))

    implementation(project(":i2-s2:group:group-f2-update"))
}
