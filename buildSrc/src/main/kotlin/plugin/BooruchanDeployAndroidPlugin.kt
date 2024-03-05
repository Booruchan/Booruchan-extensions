package plugin

import entity.AndroidDeployMetadata
import entity.AndroidDeployPackage
import entity.AndroidMetadata
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.register
import java.io.File
import java.util.Locale

private const val ANDROID_RELEASE_DEPLOY_METADATA_FILENAME = "android-deploy-metadata.json"

open class BooruchanDeployAndroidPlugin: Plugin<Project> {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

    /** Gradle wrapper script based on OS */
    private val gradleWrapper: String = when {
        osName.contains("windows") -> "gradlew.bat"
        else -> "gradlew"
    }

    override fun apply(project: Project) {
        // Task for collecting release apks in root project build directory
        project.tasks.register<Task>("collectAndroidSignedApks") {
            collectAndroidSignedApks(project)
        }

        // Task for generating metadata file for deploying android
        project.tasks.register<Task>("generateAndroidSignedDeployMetadata") {
            generateAndroidSignedDeployMetadata(project)
        }
    }

    private fun Task.collectAndroidSignedApks(project: Project) {
        project.subprojects.filter { subproject ->
            subproject.plugins.hasPlugin(type = BooruchanExtensionAndroidPlugin::class)
        }.forEach { subproject ->
            // Define directories
            val androidDirectory = File(subproject.projectDir, "build${File.separator}templates${File.separator}android")
            val androidOutputs = File(androidDirectory, "app${File.separator}build${File.separator}outputs")
            val androidRelease = File(androidOutputs, "${File.separator}apk${File.separator}release")

            // Output directory
            val androidOutput = File(subproject.rootDir, "build${File.separator}android").also { it.mkdirs() }

            // Find apk file, copy and rename
            val apk = androidRelease.listFiles().find { it.extension == "apk" && it.name.contains("signed") }!!
            val newApkName = "${subproject.name}-signed.apk"
            apk.copyTo(File(androidOutput, newApkName))

            // Find json file, copy and rename
            val json = androidRelease.listFiles().find { it.extension == "json" }!!
            val newJsonName = "${subproject.name}-metadata.json"
            json.copyTo(File(androidOutput, newJsonName))

            // Change copied json file
            val jsonContent = File(androidOutput, newJsonName).readText()
            val newJsonContent = jsonContent.replace("app-release-unsigned.apk", newApkName)
            File(androidOutput, newJsonName).writeText(newJsonContent)
        }
    }

    private fun Task.generateAndroidSignedDeployMetadata(project: Project) {
        val androidBuild = File(project.buildDir, "android")

        val androidBuildFiles = androidBuild.listFiles()
            ?: throw IllegalStateException("Could not find build/android/* files")

        val androidDeployPackages = androidBuildFiles.filter { file ->
            file.extension == "json"
        }.filter { jsonFile ->
            jsonFile.name != ANDROID_RELEASE_DEPLOY_METADATA_FILENAME
        }.map { jsonFile ->
            val metadata = json.decodeFromString<AndroidMetadata>(jsonFile.readText())
            val metadataElement = metadata.elements.first()
            val apkFile = File(androidBuild, metadataElement.outputFile)

            return@map AndroidDeployPackage(
                applicationId = metadata.applicationId,
                versionCode = metadataElement.versionCode,
                versionName = metadataElement.versionName,
                apkFilename = apkFile.name
            )
        }

        // Building json deploy metadata file
        val deployMetadata = AndroidDeployMetadata(packages = androidDeployPackages)
        val androidDeployMetadataFile = File(androidBuild, ANDROID_RELEASE_DEPLOY_METADATA_FILENAME)
        androidDeployMetadataFile.delete()
        androidDeployMetadataFile.createNewFile()
        androidDeployMetadataFile.writeText(json.encodeToString<AndroidDeployMetadata>(deployMetadata))
    }
}