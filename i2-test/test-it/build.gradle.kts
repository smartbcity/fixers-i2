plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    testImplementation(project(":i2-test:test-bdd"))
    testImplementation(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    testImplementation("org.keycloak:keycloak-admin-client:${Versions.keycloak}")
}