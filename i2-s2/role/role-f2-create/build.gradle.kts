plugins {
	id("io.spring.dependency-management")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-s2:role:role-domain"))
    api(project(":i2-s2:commons:commons-api"))

    testImplementation(project(":i2-test:test-bdd"))

}
