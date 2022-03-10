plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-f2:import:import-domain"))

    api(project(":i2-keycloak:keycloak-f2:commons:commons-api"))

    api(project(":i2-keycloak:keycloak-f2:client:client-f2-create"))
    api(project(":i2-keycloak:keycloak-f2:client:client-f2-query"))

    api(project(":i2-keycloak:keycloak-f2:realm:realm-f2-create"))
    api(project(":i2-keycloak:keycloak-f2:realm:realm-f2-query"))

    api(project(":i2-keycloak:keycloak-f2:role:role-f2-create"))
    api(project(":i2-keycloak:keycloak-f2:role:role-f2-query"))

    api(project(":i2-keycloak:keycloak-f2:user:user-f2-create"))
    api(project(":i2-keycloak:keycloak-f2:user:user-f2-query"))
    api(project(":i2-keycloak:keycloak-f2:user:user-f2-roles-grant"))

    testImplementation(project(":i2-test:test-bdd"))

}
