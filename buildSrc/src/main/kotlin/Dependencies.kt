import city.smartb.gradle.dependencies.FixersPluginVersions

object PluginVersions {
    const val springBoot = FixersPluginVersions.springBoot
    const val kotlin = FixersPluginVersions.kotlin
    const val fixers = FixersPluginVersions.fixers
}

object Versions {
    const val springBoot = PluginVersions.springBoot
    const val springOauth2 = "5.6.0"

    const val testcontainers = "1.15.2"
    const val kdatetime = "0.1.1"

    const val keycloak = "15.0.2"
    const val f2 = FixersPluginVersions.fixers
    const val s2 = FixersPluginVersions.fixers

}
