pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
}

rootProject.name = "i2"

enableFeaturePreview("GRADLE_METADATA")

include(
        "i2-spring:i2-spring-boot-starter-auth",
        "auth-test"
)