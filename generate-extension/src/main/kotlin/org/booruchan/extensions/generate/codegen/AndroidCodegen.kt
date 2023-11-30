package org.booruchan.extensions.generate.codegen

import org.booruchan.extensions.generate.template.TemplateInflater
import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCase
import java.io.File
import java.io.IOException

class AndroidCodegen(
    private val findProjectModules: FindProjectModulesUseCase,
    private val templateInflater: TemplateInflater,
) : Codegen {
    override fun invoke(context: CodegenContext) {
        // get project directory
        val projectDirectory = File(System.getProperty("user.dir")!!)

        // Find current module directory
        val currentModuleDirectory = findProjectModules(projectDirectory).find {
            it.name == CurrentModuleTitle
        }

        // Find build directory and create subdirectories
        val templatePath = "build${File.separator}templates${File.separator}android${File.separator}${File.separator}${context.sourceId}"
        val currentModuleBuildDirectory = File(currentModuleDirectory, templatePath)

        // Create current working directory
        if (!currentModuleBuildDirectory.exists()) {
            if (!currentModuleBuildDirectory.mkdirs()) {
                throw IOException("Couldn't create directory: $currentModuleBuildDirectory")
            }
        }

        generateAndroidProject(context, currentModuleBuildDirectory)
    }

    private fun generateAndroidProject(context: CodegenContext, buildDirectory: File) {
        val androidProjectDirectoryPath = this::class.java.classLoader.getResource("android")?.path
            ?: return println("Could not find \"android\" directory in resources")

        // Copy and generate android project wrapper files
        val androidProjectDirectory = File(androidProjectDirectoryPath)
        androidProjectDirectory.walkTopDown().forEach { file ->
            if (!file.isFile) return@forEach
            val destination = File(buildDirectory, file.path.removePrefix(androidProjectDirectory.path))

            // If mustache inflate file from template, copy otherwise
            if (file.extension == "mustache") {
                templateInflater.inflate(context, file, destination)
            } else {
                file.copyTo(destination, overwrite = true)
            }
        }

        // Copy kotlin files from src module
        val destination = "${buildDirectory.absolutePath}${File.separator}app${File.separator}src${File.separator}main${File.separator}kotlin"
        context.moduleRootDirectory.walkTopDown().forEach { file ->
            val path = file.absolutePath.removePrefix(context.moduleRootDirectory.absolutePath)
            val d = File(destination + path)
            file.copyTo(d, overwrite = true)
        }
    }
}