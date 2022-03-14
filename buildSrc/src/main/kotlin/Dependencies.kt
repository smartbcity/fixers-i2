import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
	val fixers = FixersPluginVersions.fixers
	const val d2 = "0.3.1"
	const val springBoot = FixersPluginVersions.springBoot
	const val kotlin = FixersPluginVersions.kotlin
}

object Versions {
    val f2 = PluginVersions.fixers
    val s2 = PluginVersions.fixers
    const val ktor = FixersVersions.Kotlin.ktor

	const val springBoot = PluginVersions.springBoot
	const val springOauth2 = "5.6.0"

	const val kdatetime = "0.1.1"
	const val keycloak = "15.0.1"

    const val testcontainers = FixersVersions.Test.testcontainers
}

object Dependencies {
	object Jvm {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}"
		)

		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core-jvm:${Versions.ktor}",
			"io.ktor:ktor-client-cio:${Versions.ktor}",
			"io.ktor:ktor-client-jackson:${Versions.ktor}"
		)
	}

	object Js {
		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core-js:${Versions.ktor}",
			"io.ktor:ktor-client-json-js:${Versions.ktor}"
		)
	}

	object Mpp {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-dsl-cqrs:${Versions.f2}",
			"city.smartb.f2:f2-dsl-function:${Versions.f2}"
		)

		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core:${Versions.ktor}",
			"io.ktor:ktor-client-serialization:${Versions.ktor}"
		)
	}
}
