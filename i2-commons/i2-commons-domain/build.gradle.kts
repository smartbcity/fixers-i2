plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
}

dependencies {
    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")

    Dependencies.mpp.ktor(::commonMainImplementation)
    Dependencies.js.ktor(::jsMainImplementation)
    Dependencies.jvm.ktor(::jvmMainImplementation)
}
