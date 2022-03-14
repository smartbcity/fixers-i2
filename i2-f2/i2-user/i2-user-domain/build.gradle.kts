plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":i2-f2::organization:organization-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:commons:commons-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:user:user-domain"))

    Dependencies.Mpp.f2(::commonMainApi)
}
