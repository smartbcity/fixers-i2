plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":i2-s2:organization:organization-domain"))

    implementation(project(":i2-s2:group:group-f2-create"))
}
