plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.plugin.serialization") version ("1.7.20")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.samskivert:jmustache:1.15")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}
