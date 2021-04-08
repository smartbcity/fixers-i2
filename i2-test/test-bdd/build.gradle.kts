plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-test:test-assertion"))
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")

    implementation("org.springframework.boot:spring-boot-starter-test:${PluginVersions.springBoot}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

}

apply(from = rootProject.file("gradle/publishing.gradle"))