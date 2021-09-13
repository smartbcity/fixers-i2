import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("plugin.spring") version PluginVersionsLocal.kotlin apply false
    id("io.spring.dependency-management") version PluginVersionsLocal.springPom apply false
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

subprojects {
    plugins.withType(io.spring.gradle.dependencymanagement.DependencyManagementPlugin::class.java).whenPluginAdded {
        the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
            imports {
                mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
            }
        }
    }
}
