import task.GenerateCodegenProject
import task.GenerateCodegenContext

plugins {
    `java-library`
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version ("7.1.1")
    id("org.jetbrains.kotlin.plugin.serialization") version ("1.7.20")
}
group = "org.booruchan.extensions.lolibooru"
version = "0.3.4"

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

tasks {
    register<GenerateCodegenProject>("generateAndroidProject") {
        type = GenerateCodegenProject.Type.Android
        dependsOn("generateCodegenContext")
    }

    register<GenerateCodegenContext>("generateCodegenContext") {
        classname = "LolibooruSource"
        title = "Lolibooru"
    }

//    register<Exec>("assembleAndroidDebug") {
//        workingDir = File(buildDir, "templates${File.separator}android")
//        commandLine("gradlew.bat")
//        this.
//        dependsOn("generateAndroidProject")
//    }

    register<GradleBuild>("assembleAndroidDebug") {
        dir = File(buildDir, "templates${File.separator}android")
        tasks = listOf(":app:assembleDebug")
        dependsOn("generateAndroidProject")
    }
}
