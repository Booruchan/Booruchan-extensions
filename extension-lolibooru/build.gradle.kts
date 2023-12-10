import org.jetbrains.kotlin.backend.common.phaser.namedUnitPhase

plugins {
    `java-library`
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version ("7.1.1")
    id("org.jetbrains.kotlin.plugin.serialization") version ("1.7.20")
}
version = "0.3.4"

val title = "Lolibooru"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":extension-sdk"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    implementation(kotlin("stdlib-jdk8"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
