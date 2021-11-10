plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":i2-spring:i2-spring-boot-starter-auth"))

	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
	testImplementation("io.projectreactor:reactor-test:3.4.11")
}
