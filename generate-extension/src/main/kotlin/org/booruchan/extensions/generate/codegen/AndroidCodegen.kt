package org.booruchan.extensions.generate.codegen

import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCase
import java.io.File
import java.io.IOException

class AndroidCodegen(
    private val findProjectModules: FindProjectModulesUseCase,
) : Codegen {
    override fun invoke(context: CodegenContext) {
        // get project directory
        val projectDirectory = File(System.getProperty("user.dir")!!)

        // Find current module directory
        val currentModuleDirectory = findProjectModules(projectDirectory).find {
            it.name == CurrentModuleTitle
        }

        // Find build directory and create subdirectories
        val templatePath = "build${File.separator}templates${File.separator}android${File.separator}"
        val currentModuleBuildDirectory = File(currentModuleDirectory, templatePath)

        // Create current working directory
        if (!currentModuleBuildDirectory.exists()) {
            if (!currentModuleBuildDirectory.mkdirs()) {
                throw IOException("Couldn't create directory: $currentModuleBuildDirectory")
            }
        }

        sas(context, currentModuleBuildDirectory)
    }

    private fun sas(context: CodegenContext, buildDirectory: File) {
        val androidDirectoryPath = this::class.java.classLoader.getResource("android")?.path
            ?: return println("Could not find \"android\" directory in resources")

        val androidDirectory = File(androidDirectoryPath)
        androidDirectory.walkTopDown().forEach { file ->
            if (!file.isFile) return@forEach
            val destination = File(buildDirectory, file.path.removePrefix(androidDirectory.path))

            if (file.extension == "mustache") {
                // TODO: generate
                file.copyTo(destination, overwrite = true)
            } else {
                file.copyTo(destination, overwrite = true)
            }
        }
    }
}