import plugin.BooruchanExtensionAndroidPlugin

buildscript {
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

plugins {
    kotlin("jvm") version "1.7.20"
    `maven-publish`
}

allprojects {
    group = "org.booruchan.extensions"
}

subprojects {
    val subprojectVersion = this.version.toString()
    val subprojectArtifact = this.name

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        withSourcesJar()
        withJavadocJar()
    }

    publishing {
        publications {
            register<MavenPublication>("maven") {
                // Library Package Name
                // NOTE : Different GroupId For Each Library / Module, So That Each Library Is Not Overwritten
                groupId = "org.booruchan.extensions"

                // Library Name / Module Name
                // NOTE : Different ArtifactId For Each Library / Module, So That Each Library Is Not Overwritten
                artifactId = subprojectArtifact

                // Version Library Name (Example : "1.0.0")
                version = subprojectVersion

                afterEvaluate {
                    from(components["java"])
                }
            }
        }
    }
}

// Task for collecting release apks in root project build directory
tasks.register<Task>("collectAndroidReleaseApks") {
    project.subprojects.filter { project ->
        project.plugins.hasPlugin(type = BooruchanExtensionAndroidPlugin::class)
    }.forEach { project ->
        // Define directories
        val androidDirectory = File(project.projectDir, "build${File.separator}templates${File.separator}android")
        val androidOutputs = File(androidDirectory, "app${File.separator}build${File.separator}outputs")
        val androidRelease = File(androidOutputs, "${File.separator}apk${File.separator}release")

        // Output directory
        val androidOutput = File(project.rootDir, "build${File.separator}android").also { it.mkdirs() }

        // Find apk file, copy and rename
        val apk = androidRelease.listFiles().find { it.extension == "apk" }!!
        val newApkName = "${project.name}-release-unsigned.apk"
        apk.copyTo(File(androidOutput, newApkName))

        // Find json file, copy and rename
        val json = androidRelease.listFiles().find { it.extension == "json" }!!
        val newJsonName = "${project.name}-release-metadata.json"
        json.copyTo(File(androidOutput, newJsonName))

        // Change copied json file
        val jsonContent = File(androidOutput, newJsonName).readText()
        val newJsonContent = jsonContent.replace("app-release-unsigned.apk", newApkName)
        File(androidOutput, newJsonName).writeText(newJsonContent)
    }
}