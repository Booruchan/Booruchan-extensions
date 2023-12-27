import plugin.BooruchanExtensionAndroidPlugin
import task.GenerateCodegenContext

plugins {
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version ("1.7.20")
}

group = "org.booruchan.extensions.safebooru"
version = "0.3.5"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":sdk"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jsoup:jsoup:1.17.1")

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

apply<BooruchanExtensionAndroidPlugin>()

tasks.named<GenerateCodegenContext>("generateCodegenContext") {
    classname = "SafebooruSource"
    title = "Safebooru"
}
