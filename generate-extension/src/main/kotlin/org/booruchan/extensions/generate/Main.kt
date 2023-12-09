package org.booruchan.extensions.generate

import io.github.oshai.kotlinlogging.KotlinLogging
import org.booruchan.extensions.generate.codegen.AndroidCodegen
import org.booruchan.extensions.generate.codegen.Codegen
import org.booruchan.extensions.generate.codegen.CodegenContext
import org.booruchan.extensions.generate.codegen.CodegenContextFactory
import org.booruchan.extensions.generate.command.WindowsGradleAssembleDebugCommand
import org.booruchan.extensions.generate.logger.Logger
import org.booruchan.extensions.generate.logger.LoggerImpl
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

private val LoggerModule = module {
    single<Logger> { LoggerImpl() }
}

fun main(): Unit = startKoin {
    modules(MainModule, AndroidModule, LoggerModule)
}.run { Main()() }

class Main : KoinComponent {
    private val findProjectModules: FindProjectModulesUseCase by inject()
    private val findSourceClassFile: FindSourceClassFileUseCase by inject()

    private val codegenContextFactory: CodegenContextFactory by inject()

    private val codeGenerators: List<Codegen> by inject()

    private val logger: Logger by inject()

    operator fun invoke() {
        buildContexts().onEach { context ->
            logger.info { "Generating code for context: ${context.sourceId}" }
            codeGenerators.forEach { codegen -> codegen(context) }
        }.onEach { context ->
            logger.info { "Assembling code for context: ${context.sourceId}" }
            WindowsGradleAssembleDebugCommand(logger)(context.buildDirectory)
        }.forEach {
            logger.info { "Finished: ${it.sourceId}" }
        }
    }

    // Find all project modules, file with Source class and build context for code generation
    private fun buildContexts(): Sequence<CodegenContext> {
        // Get project directory
        val projectDirectory = File(System.getProperty("user.dir")!!)
        logger.info { "Project directory: $projectDirectory" }

        return sequenceOf(*findProjectModules(projectDirectory).toTypedArray()).map { projectModuleFile ->
            findSourceClassFile(projectModuleFile)
        }.filterNotNull().map { sourceClassFile ->
            codegenContextFactory.createCodegenContext(sourceClassFile)
        }.onEach {
            logger.info { "Generated context: CodegenContext(sourceId=${it.sourceId})" }
        }
    }
}