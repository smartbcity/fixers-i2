plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {

    api(project(":i2-s2:client:client-f2-create"))
    api(project(":i2-s2:client:client-f2-generate-secret"))
    api(project(":i2-s2:client:client-f2-query"))
    api(project(":i2-s2:client:client-f2-update"))

    api(project(":i2-s2:import:import-data"))

    api(project(":i2-s2:realm:realm-f2-create"))
    api(project(":i2-s2:realm:realm-f2-query"))

    api(project(":i2-s2:role:role-f2-add-composites"))
    api(project(":i2-s2:role:role-f2-create"))
    api(project(":i2-s2:role:role-f2-query"))

    api(project(":i2-s2:user:user-f2-create"))
    api(project(":i2-s2:user:user-f2-disable"))
    api(project(":i2-s2:user:user-f2-delete"))
    api(project(":i2-s2:user:user-f2-query"))
    api(project(":i2-s2:user:user-f2-password-reset"))
    api(project(":i2-s2:user:user-f2-roles-grant"))
    api(project(":i2-s2:user:user-f2-roles-revoke"))
    api(project(":i2-s2:user:user-f2-update"))

    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    api("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
    implementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
}
