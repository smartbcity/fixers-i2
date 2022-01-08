import city.smartb.gradle.dependencies.FixersPluginVersions

object PluginVersions {
    const val fixers = "0.3.1"
    const val d2 = fixers
    const val springBoot = FixersPluginVersions.springBoot
    const val kotlin = FixersPluginVersions.kotlin
}

object Versions {
    const val springBoot = PluginVersions.springBoot
    const val springOauth2 = "5.6.0"

    const val testcontainers = "1.15.2"
    const val kdatetime = "0.1.1"

    const val keycloak = "15.0.2"
    const val f2 = PluginVersions.fixers
    const val s2 = PluginVersions.fixers
}
