plugins {
    id("io.spring.dependency-management")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-reflect")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
    implementation( "org.springframework.security:spring-security-oauth2-jose")

    implementation("org.springframework.boot:spring-boot-autoconfigure")

    implementation("io.projectreactor:reactor-core:${Versions.reactor}")
}
