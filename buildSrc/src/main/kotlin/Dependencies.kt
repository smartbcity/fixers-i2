import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
	val fixers = FixersPluginVersions.fixers
	val d2 = FixersPluginVersions.fixers
	const val springBoot = FixersPluginVersions.springBoot
	const val kotlin = FixersPluginVersions.kotlin
	const val graalvm = FixersPluginVersions.graalvm
	const val shadowJar = "7.1.2"
}

object Versions {
	val f2 = PluginVersions.fixers
	const val ktor = FixersVersions.Kotlin.ktor

	const val springBoot = PluginVersions.springBoot
	const val springOauth2 = "6.0.1"

	const val kdatetime = "0.1.1"
	const val keycloak = "18.0.2"

	const val testcontainers = FixersVersions.Test.testcontainers
	const val slf4j = FixersVersions.Logging.slf4j
}

object Dependencies {
	object Jvm {
		fun slf4j(scope: Scope) = scope.add(
			"org.slf4j:slf4j-api:${Versions.slf4j}",
		)
		fun f2Http(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}"
		)
		fun f2Function(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}"
		)

		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core:${Versions.ktor}",
			"io.ktor:ktor-client-content-negotiation:${Versions.ktor}",
			"io.ktor:ktor-client-cio:${Versions.ktor}",
			"io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}",
			"io.ktor:ktor-serialization-jackson:${Versions.ktor}"
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
