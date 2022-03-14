plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))
    api(project(":i2-spring:i2-spring-boot-starter-auth"))

    api(project(":i2-keycloak:keycloak-f2:client:client-domain"))
    implementation(project(":i2-keycloak:keycloak-f2:client:client-f2-create"))
    implementation(project(":i2-keycloak:keycloak-f2:client:client-f2-query"))

    api(project(":i2-keycloak:keycloak-f2:realm:realm-domain"))

    api(project(":i2-keycloak:keycloak-f2:role:role-domain"))
    implementation(project(":i2-keycloak:keycloak-f2:role:role-f2-add-composites"))
    implementation(project(":i2-keycloak:keycloak-f2:role:role-f2-create"))
    implementation(project(":i2-keycloak:keycloak-f2:role:role-f2-query"))

    api(project(":i2-keycloak:keycloak-f2:user:user-domain"))
    implementation(project(":i2-keycloak:keycloak-f2:user:user-f2-create"))
    implementation(project(":i2-keycloak:keycloak-f2:user:user-f2-query"))
    implementation(project(":i2-keycloak:keycloak-f2:user:user-f2-roles-grant"))
}
