package plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.GradleBuild
import org.gradle.kotlin.dsl.register
import task.GenerateCodegenContext
import task.GenerateCodegenProject
import java.io.File
import java.util.*

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
    }
}
