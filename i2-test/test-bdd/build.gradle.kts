plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {

    api(project(":i2-s2:client:client-f2-create"))

    api(project(":i2-s2:realm:realm-f2-create"))
    api(project(":i2-s2:realm:realm-f2-query"))

    api(project(":i2-s2:role:role-f2-create"))

    api(project(":i2-s2:user:user-f2-create"))
    api(project(":i2-s2:user:user-f2-password-reset"))
    api(project(":i2-s2:user:user-f2-roles-grant"))
    api(project(":i2-s2:user:user-f2-roles-revoke"))

    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))
    api("org.testcontainers:junit-jupiter:${Versions.testcontainers}")

}
