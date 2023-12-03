package org.booruchan.extensions.generate.codegen

import org.booruchan.extension.sdk.Source
import org.booruchan.extensions.generate.usecases.FindPackageUseCase
import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCase
import org.booruchan.extensions.generate.usecases.FindSourceRootFileUseCase
import java.io.File

class CodegenContextFactory(
    private val findProjectModules: FindProjectModulesUseCase,
    private val findSourceRootFile: FindSourceRootFileUseCase,
    private val findPackage: FindPackageUseCase,
) {
    fun createCodegenContext(sourceClassFile: File): CodegenContext {
        val sourcePackage = findPackage(sourceClassFile)
        val sourceClass = Class.forName("$sourcePackage.${sourceClassFile.nameWithoutExtension}")
        val sourceInstance = sourceClass.getDeclaredConstructor().newInstance() as Source

        return CodegenContext(
            sourceClassFile = sourceClassFile,
            moduleRootDirectory = findSourceRootFile(sourceClassFile),
            buildDirectory = getBuildDirectory(sourceInstance.id),
            sourcePackage = sourcePackage,
            sourceTitle = sourceInstance.title,
            sourceClassName = sourceClassFile.nameWithoutExtension,
            sourceId = sourceInstance.id,
        )
    }

    private fun getBuildDirectory(sourceId: String): File {
        // get project directory
        val projectDirectory = File(System.getProperty("user.dir")!!)

        // Find current module directory
        val currentModuleDirectory = findProjectModules(projectDirectory).find {
            it.name == CurrentModuleTitle
        }

        // Find build directory and create subdirectories
        // ..\build\templates\android\{sourceId}
        val templatePath = "build${File.separator}templates${File.separator}android${File.separator}${sourceId}"
        return File(currentModuleDirectory, templatePath)
    }
}