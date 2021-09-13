plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-s2:client:client-domain"))

    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")

    testImplementation(project(":i2-test:test-bdd"))

}

apply(from = rootProject.file("gradle/publishing.gradle"))
