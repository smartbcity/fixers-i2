plugins {
	id("io.spring.dependency-management")
	id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":i2-spring:i2-spring-boot-starter-auth"))

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}
