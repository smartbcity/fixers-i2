plugins {
    kotlin("plugin.spring") version PluginVersions.kotlin apply false
    kotlin("plugin.serialization") version PluginVersions.kotlin apply false
    kotlin("kapt") version PluginVersions.kotlin apply false
    id("org.springframework.boot") version PluginVersions.springBoot apply false
    id("org.graalvm.buildtools.native") version PluginVersions.graalvm apply false

    id("city.smartb.fixers.gradle.config") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.sonar") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.d2") version PluginVersions.d2

    id("city.smartb.fixers.gradle.kotlin.mpp") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.kotlin.jvm") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.publish") version PluginVersions.fixers apply false
}

allprojects {
    group = "city.smartb.i2"
    version = System.getenv("VERSION") ?: "local"
    repositories {
        mavenCentral()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        mavenLocal()
    }
}


fixers {
    d2 {
        outputDirectory = file("storybook/d2/")
    }
    bundle {
        id = "i2"
        name = "I2"
        description = "Identity and Authentication functions"
        url = "https://gitlab.smartb.city/fixers/i2"
    }
}
