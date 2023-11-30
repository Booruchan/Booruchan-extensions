package org.booruchan.extensions.generate

import org.booruchan.extensions.generate.codegen.AndroidCodegen
import org.booruchan.extensions.generate.codegen.Codegen
import org.booruchan.extensions.generate.codegen.CodegenContext
import org.booruchan.extensions.generate.usecases.FindPackageUseCase
import org.booruchan.extensions.generate.usecases.FindPackageUseCaseImpl
import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCase
import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCaseImpl
import org.booruchan.extensions.generate.usecases.FindSourceClassFileUseCase
import org.booruchan.extensions.generate.usecases.FindSourceClassFileUseCaseRegexp
import org.booruchan.extensions.generate.usecases.FindSourceRootFileUseCase
import org.booruchan.extensions.generate.usecases.FindSourceRootFileUseCaseImpl
import java.io.File

fun main() {
    val findProjectModules = FindProjectModulesUseCaseImpl()
    val findSourceRootFile = FindSourceRootFileUseCaseImpl()
    val findSourceClassFiles = FindSourceClassFileUseCaseRegexp()
    val findPackage = FindPackageUseCaseImpl(findSourceRootFile = findSourceRootFile)

    Main(
        findProjectModules = findProjectModules,
        findSourceClassFile = findSourceClassFiles,
        findSourceRootFile = findSourceRootFile,
        findPackage = findPackage,
        codegen = AndroidCodegen(findProjectModules = findProjectModules),
    )()
}

class Main(
    private val findProjectModules: FindProjectModulesUseCase,
    private val findSourceClassFile: FindSourceClassFileUseCase,
    private val findSourceRootFile: FindSourceRootFileUseCase,
    private val findPackage: FindPackageUseCase,
    private val codegen: Codegen,
) {
    operator fun invoke() {
        // Get project directory
        val projectDirectory = File(System.getProperty("user.dir")!!)
        // Find all project modules
        val projectModules = findProjectModules(projectDirectory)
        // Find all files with Source class
        val sourceClassFiles = projectModules.map { projectModuleFile ->
            findSourceClassFile(projectModuleFile)
        }
        // Build contexts for code generation
        val contexts = sourceClassFiles.filterNotNull().map { sourceClassFile ->
            buildCodegenContext(sourceClassFile)
        }
        // Generate code for the specific codegen with provided context
        contexts.forEach { context -> codegen(context) }
    }

    private fun buildCodegenContext(sourceClassFile: File) = CodegenContext(
        source = sourceClassFile,
        root = findSourceRootFile(sourceClassFile),
        `package` = findPackage(sourceClassFile),
    )

}
