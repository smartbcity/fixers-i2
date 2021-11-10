plugins {
    kotlin("plugin.spring") version PluginVersionsLocal.kotlin apply false
    id("org.springframework.boot") version PluginVersionsLocal.springBoot apply false

    id("city.smartb.fixers.gradle.config") version PluginVersionsLocal.fixers
    id("city.smartb.fixers.gradle.sonar") version PluginVersionsLocal.fixers
    id("city.smartb.fixers.gradle.d2") version PluginVersionsLocal.fixers
}

allprojects {
    group = "city.smartb.i2"
    version = System.getenv("VERSION") ?: "latest"
    repositories {
        mavenCentral()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

fixers {
    bundle {
        id = "i2"
        name = "I2"
        description = "Identity and Authentification functions"
        url = "https://gitlab.smartb.city/fixers/i2"
    }
}
