plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-security:${Versions.springBoot}")
    api("org.springframework.security:spring-security-oauth2-resource-server:${Versions.springOauth2}")
    api( "org.springframework.security:spring-security-oauth2-jose:${Versions.springOauth2}")

    implementation("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")

    implementation("io.projectreactor:reactor-core:${Versions.reactor}")
}
