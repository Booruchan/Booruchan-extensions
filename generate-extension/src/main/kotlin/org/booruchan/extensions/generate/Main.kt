package org.booruchan.extensions.generate

import org.booruchan.extensions.generate.codegen.AndroidCodegen
import org.booruchan.extensions.generate.codegen.Codegen
import org.booruchan.extensions.generate.codegen.CodegenContext
import org.booruchan.extensions.generate.codegen.CodegenContextFactory
import org.booruchan.extensions.generate.command.WindowsGradleAssembleDebugCommand
import org.booruchan.extensions.generate.template.MustacheTemplateInflater
import org.booruchan.extensions.generate.template.TemplateInflater
import org.booruchan.extensions.generate.usecases.FindPackageUseCase
import org.booruchan.extensions.generate.usecases.FindPackageUseCaseImpl
import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCase
import org.booruchan.extensions.generate.usecases.FindProjectModulesUseCaseImpl
import org.booruchan.extensions.generate.usecases.FindSourceClassFileUseCase
import org.booruchan.extensions.generate.usecases.FindSourceClassFileUseCaseRegexp
import org.booruchan.extensions.generate.usecases.FindSourceRootFileUseCase
import org.booruchan.extensions.generate.usecases.FindSourceRootFileUseCaseImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.io.File

private val MainModule = module {
    single<FindProjectModulesUseCase> { FindProjectModulesUseCaseImpl() }
    single<FindSourceRootFileUseCase> { FindSourceRootFileUseCaseImpl() }
    single<FindSourceClassFileUseCase> { FindSourceClassFileUseCaseRegexp() }
    single<FindPackageUseCase> { FindPackageUseCaseImpl(get()) }

    single<CodegenContextFactory> { CodegenContextFactory(get(), get(), get()) }
}

private val AndroidModule = module {
    single<TemplateInflater> { MustacheTemplateInflater() }
    single<List<Codegen>> { listOf(AndroidCodegen(get(), get())) }
}

fun main(): Unit = startKoin {
    modules(MainModule, AndroidModule)
}.run { Main()() }

class Main : KoinComponent {
    private val findProjectModules: FindProjectModulesUseCase by inject()
    private val findSourceClassFile: FindSourceClassFileUseCase by inject()

    private val codegenContextFactory: CodegenContextFactory by inject()

    private val codeGenerators: List<Codegen> by inject()

    operator fun invoke() {
        val contexts = buildContexts()

        // Generate code for each codegen with provided context
        contexts.forEach { context -> codeGenerators.forEach { codegen -> codegen(context) } }

        contexts.forEach { context ->
            WindowsGradleAssembleDebugCommand()(context.buildDirectory)
        }
    }

    private fun buildContexts(): List<CodegenContext> {
        // Get project directory
        val projectDirectory = File(System.getProperty("user.dir")!!)

        // Find all project modules
        val projectModules = findProjectModules(projectDirectory)

        // Find all files with Source class
        val sourceClassFiles = projectModules.map { projectModuleFile ->
            findSourceClassFile(projectModuleFile)
        }

        // Build contexts for code generation
        return sourceClassFiles.filterNotNull().map { sourceClassFile ->
            codegenContextFactory.createCodegenContext(sourceClassFile)
        }
    }
}