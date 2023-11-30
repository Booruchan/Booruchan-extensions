package org.booruchan.extensions.generate.codegen

import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCase
import java.io.File

class AndroidCodegen(
    private val findProjectModules: FindProjectModulesUseCase,
): Codegen {
    override fun invoke(context: CodegenContext) {
        // Find current module directory
        val currentModuleDirectory = findProjectModules(File(System.getProperty("user.dir")!!)).find {
            it.name == CurrentModuleTitle
        }
        // Find build directory and create subdirectories
        val templatePath = "build${File.separator}templates${File.separator}android${File.separator}"
        val currentModuleBuildDirectory = File(currentModuleDirectory, templatePath)
        // Create current working directory
        if (!currentModuleBuildDirectory.exists()) {
            if (!currentModuleBuildDirectory.mkdirs()) {
                return println("Couldn't create directories ")
            }
        }

        sas(context, currentModuleBuildDirectory)
    }

    private fun sas(context: CodegenContext, directory: File) {
        val androidDirectoryPath = this::class.java.classLoader.getResource("android")?.path
            ?: return println("Could not find \"android\" directory in resources")

        val androidDirectory = File(androidDirectoryPath)
        androidDirectory.walkTopDown().forEach { file ->
            if (!file.isFile) return@forEach
            val destination = File(directory, file.path.removePrefix(androidDirectory.path))

            if (file.extension == "mustache") {
                // TODO: generate
                file.copyTo(destination, overwrite = true)
            } else {
                file.copyTo(destination, overwrite = true)
            }
        }
    }
}