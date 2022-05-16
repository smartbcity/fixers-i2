plugins {
    kotlin("plugin.spring") version PluginVersions.kotlin apply false
    kotlin("plugin.serialization") version PluginVersions.kotlin apply false
    kotlin("kapt") version PluginVersions.kotlin apply false
    id("org.springframework.boot") version PluginVersions.springBoot apply false

    id("city.smartb.fixers.gradle.config") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.sonar") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.d2") version PluginVersions.d2

    id("city.smartb.fixers.gradle.kotlin.mpp") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.kotlin.jvm") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.publish") version PluginVersions.fixers apply false
}

allprojects {
    group = "city.smartb.i2"
    version = System.getenv("VERSION") ?: "latest"
    repositories {
        mavenCentral()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

fixers {
    bundle {
        id = "i2"
        name = "I2"
        description = "Identity and Authentication functions"
        url = "https://gitlab.smartb.city/fixers/i2"
    }
    d2 {
        outputDirectory = file("storybook/stories/d2")
    }
}
