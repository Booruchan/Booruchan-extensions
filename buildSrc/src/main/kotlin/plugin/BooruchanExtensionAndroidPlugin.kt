package plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild
import org.gradle.kotlin.dsl.register
import task.GenerateCodegenContext
import task.GenerateCodegenProject
import java.io.File

/** Provides several gradle tasks for generating extensions and installing them on android device */
class BooruchanExtensionAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // For generating context for inflating template files
        project.tasks.register<GenerateCodegenContext>("generateCodegenContext")

        // Generate
        project.tasks.register<GenerateCodegenProject>("generateAndroidProject") {
            type = GenerateCodegenProject.Type.Android
            dependsOn("generateCodegenContext")
        }

        project.tasks.register<GradleBuild>("assembleAndroidDebug") {
            dir = File(project.buildDir, "templates${File.separator}android")
            tasks = listOf(":app:assembleDebug")
            dependsOn("generateAndroidProject")
        }

        project.tasks.register<GradleBuild>("assembleAndroidRelease") {
            dir = File(project.buildDir, "templates${File.separator}android")
            tasks = listOf(":app:assembleRelease")
            dependsOn("generateAndroidProject")
        }

        project.tasks.register<GradleBuild>("installAndroidRelease") {
            dir = File(project.buildDir, "templates${File.separator}android")
            tasks = listOf(":app:installRelease")
            dependsOn("generateAndroidProject")
        }

        project.tasks.register<GradleBuild>("installAndroidDebug") {
            dir = File(project.buildDir, "templates${File.separator}android")
            tasks = listOf(":app:installDebug")
            dependsOn("generateAndroidProject")
        }
    }
}
