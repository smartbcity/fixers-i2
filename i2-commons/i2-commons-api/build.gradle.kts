plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
}

dependencies {
    api(project(":i2-commons:i2-commons-domain"))
    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")
}
