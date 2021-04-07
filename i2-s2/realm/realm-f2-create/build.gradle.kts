plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-s2:realm:realm-domain"))

    api(project(":i2-keycloak:keycloak-master:keycloak-master-client"))

    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${PluginVersions.springBoot}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

}

apply(from = rootProject.file("gradle/publishing.gradle"))