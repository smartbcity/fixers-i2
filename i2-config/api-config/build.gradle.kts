plugins {
    id("org.springframework.boot")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
    implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")

    implementation(project(":i2-config:api-auth"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName = "smartbcity/i2-config"
}
