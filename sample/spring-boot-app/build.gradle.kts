plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":i2-spring:i2-spring-boot-starter-auth"))

	implementation(project(":i2-s2:organization:organization-f2-create"))
	implementation(project(":i2-s2:organization:organization-f2-query"))
	implementation(project(":i2-s2:organization:organization-f2-update"))

	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
	testImplementation("io.projectreactor:reactor-test:3.4.11")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
	imageName = "smartbcity/i2-gateway:${this.project.version}"
}
