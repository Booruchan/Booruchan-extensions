package plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.register
import task.GenerateCodegenContext
import task.GenerateCodegenProject
import java.io.File
import java.io.FileNotFoundException
import java.util.Locale
import java.util.Properties

/** Provides several gradle tasks for generating extensions and installing them on android device */
class BooruchanExtensionAndroidPlugin : Plugin<Project> {

    private val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

    /** Gradle wrapper script based on OS */
    private val gradleWrapper: String = when {
        osName.contains("windows") -> "gradlew.bat"
        else -> "gradlew"
    }

    override fun apply(project: Project) {
        // For generating context for inflating template files
        project.tasks.register<GenerateCodegenContext>("generateCodegenContext")

        // Generate
        project.tasks.register<GenerateCodegenProject>("generateAndroidProject") {
            type = GenerateCodegenProject.Type.Android
            dependsOn("generateCodegenContext")
        }

        project.tasks.register<Exec>("assembleAndroidDebug") {
            workingDir = File(project.buildDir, "templates${File.separator}android")
            commandLine(File(project.rootDir, gradleWrapper).absolutePath, ":app:assembleDebug")
            dependsOn("generateAndroidProject")
        }

        project.tasks.register<Exec>("assembleAndroidRelease") {
            workingDir = File(project.buildDir, "templates${File.separator}android")
            commandLine(File(project.rootDir, gradleWrapper).absolutePath, ":app:assembleRelease")
            dependsOn("generateAndroidProject")
        }

        project.tasks.register<Exec>("installAndroidDebug") {
            workingDir = File(project.buildDir, "templates${File.separator}android")
            commandLine(File(project.rootDir, gradleWrapper).absolutePath, ":app:installDebug")
            dependsOn("generateAndroidProject")
        }

        project.tasks.register<Exec>("installAndroidRelease") {
            workingDir = File(project.buildDir, "templates${File.separator}android")
            commandLine(File(project.rootDir, gradleWrapper).absolutePath, ":app:installRelease")
            dependsOn("generateAndroidProject")
        }

        project.tasks.register<Exec>("assembleAlignedAndroidRelease") {
            println("Environment: ${System.getenv("ANDROID_SDK_ROOT")}")
            val properties = Properties().apply { load(project.rootProject.file("local.properties").reader()) }
            val sdkDirectory = File(properties["sdk.dir"].toString())
            val zipalign = sdkDirectory.walkTopDown().find { it.isFile && it.nameWithoutExtension == "zipalign" }
                ?: throw FileNotFoundException("Could not find zipalign. Does your local properties contains android sdk directory")

            val inputApkPath =
                "${File.separator}templates${File.separator}android${File.separator}app${File.separator}build${File.separator}outputs${File.separator}apk${File.separator}release${File.separator}app-release-unsigned.apk"
            val inputApk = File(project.buildDir, inputApkPath)

            val outputApkPath =
                "${File.separator}templates${File.separator}android${File.separator}app${File.separator}build${File.separator}outputs${File.separator}apk${File.separator}release${File.separator}app-release-aligned.apk"
            val outputApk = File(project.buildDir, outputApkPath)

            commandLine(zipalign, "-v", "-p", 4, inputApk, outputApk)

            dependsOn("assembleAndroidRelease")
        }

        project.tasks.register<Exec>("assembleSignedAndroidRelease") {
            println("Environment: ${System.getenv("ANDROID_SDK_ROOT")}")
            val properties = Properties().apply { load(project.rootProject.file("local.properties").reader()) }
            val sdkDirectory = File(properties["sdk.dir"].toString())
            val apksigner = sdkDirectory.walkTopDown().find { it.isFile && it.nameWithoutExtension == "apksigner" }
                ?: throw FileNotFoundException("Could not find apksigner. Does your local properties contains android sdk directory")

            val inputApkPath =
                "${File.separator}templates${File.separator}android${File.separator}app${File.separator}build${File.separator}outputs${File.separator}apk${File.separator}release${File.separator}app-release-aligned.apk"
            val inputApk = File(project.buildDir, inputApkPath)

            val outputApkPath =
                "${File.separator}templates${File.separator}android${File.separator}app${File.separator}build${File.separator}outputs${File.separator}apk${File.separator}release${File.separator}app-release-signed.apk"
            val outputApk = File(project.buildDir, outputApkPath)

            val keystore = File(project.rootDir, project.property("jks")!!.toString())
            val keyalias = project.property("alias")!!.toString()
            val keypass = project.property("pass")!!.toString()

            commandLine(
                apksigner, "sign",
                "--ks", keystore,
                "--ks-key-alias", keyalias,
                "--out", outputApk,
                "--ks-pass", keypass,
                inputApk,
            )

            dependsOn("assembleAlignedAndroidRelease")
        }
    }
}
