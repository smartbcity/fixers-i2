plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":i2-s2:group:group-domain"))
    api(project(":i2-s2:commons:commons-api"))
}
