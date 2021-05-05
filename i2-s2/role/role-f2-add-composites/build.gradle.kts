plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-s2:role:role-domain"))
    api(project(":i2-s2:commons:commons-api"))

    testImplementation(project(":i2-test:test-bdd"))

}

apply(from = rootProject.file("gradle/publishing.gradle"))