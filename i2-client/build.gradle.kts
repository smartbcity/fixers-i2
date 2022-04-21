plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	api(project(":i2-f2:i2-role:i2-role-domain"))
	api(project(":i2-f2:organization:organization-domain"))

	api("io.ktor:ktor-client-jackson:${Versions.ktor}")
	api("io.ktor:ktor-client-cio:${Versions.ktor}")

}
