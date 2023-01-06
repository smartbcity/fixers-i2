plugins {
    id("org.springframework.boot")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
    implementation(project(":i2-app:init:app-init-service"))
    Dependencies.Jvm.slf4j(::implementation)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("smartbcity/i2-init:${this.project.version}")
}
