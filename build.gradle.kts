import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version PluginVersionsLocal.kotlin apply false
    kotlin("jvm") version PluginVersionsLocal.kotlin apply false
    kotlin("plugin.spring") version PluginVersionsLocal.kotlin apply false
    id("io.spring.dependency-management") version PluginVersionsLocal.springPom apply false
    id("org.springframework.boot") version PluginVersionsLocal.springBoot apply false
    id("com.google.cloud.tools.jib") version PluginVersionsLocal.jib apply false

    id("city.smartb.fixers.gradle.config") version PluginVersionsLocal.fixers
    id("city.smartb.fixers.gradle.sonar") version PluginVersionsLocal.fixers
    id("city.smartb.fixers.gradle.d2") version PluginVersionsLocal.fixers
}

allprojects {
    group = "city.smartb.i2"
    version = System.getenv("VERSION") ?: "latest"
    repositories {
        jcenter()
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
    plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
        the<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>().apply {
            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = "11"
                }
            }
            js(IR) {
                browser {
                }
                binaries.executable()
            }
            sourceSets {
                val commonMain by getting
                val commonTest by getting {
                    dependencies {
                        implementation(kotlin("test-common"))
                        implementation(kotlin("test-annotations-common"))
                    }
                }
                val jvmMain by getting
                val jvmTest by getting {
                    dependencies {
                        implementation(kotlin("reflect"))
                        implementation(kotlin("test-junit"))
                    }
                }
                val jsMain by getting
                val jsTest by getting {
                    dependencies {
                        implementation(kotlin("test-js"))
                    }
                }
            }
        }
    }

    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        tasks.withType<KotlinCompile>().configureEach {
            println("Configuring $name in project ${project.name}...")
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }

        }
        the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
            imports {
                mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) {
                    bomProperty("kotlin.version", PluginVersionsLocal.kotlin)
                }
            }
        }
        tasks.withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_11.toString()
            targetCompatibility = JavaVersion.VERSION_11.toString()
        }

        tasks.withType<Test> {
            useJUnitPlatform()
        }

        dependencies {
            val implementation by configurations
            val testImplementation by configurations

            implementation(kotlin("reflect"))

            implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")

            testImplementation("org.junit.jupiter:junit-jupiter:${Versions.junit}")
            testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")

            testImplementation("org.assertj:assertj-core:${Versions.assertj}")

            implementation("org.springframework.boot:spring-boot-starter-test:${PluginVersionsLocal.springBoot}") {
                exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
            }
        }
    }
}
