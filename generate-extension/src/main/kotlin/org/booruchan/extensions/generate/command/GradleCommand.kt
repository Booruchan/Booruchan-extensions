package org.booruchan.extensions.generate.command

import org.booruchan.extensions.generate.logger.Logger
import org.koin.core.context.startKoin
import java.io.File

/** Command that can be invoked on generated gradle project */
abstract class GradleCommand(private val logger: Logger) {

    /** Gradle invocation. Can be different for OS */
    abstract val gradle: String

    /** Command that will be invoked */
    abstract val command: String

    operator fun invoke(projectRootDirectory: File): Int {
        val commandString = "$gradle $command".split(" ")

        val processBuilder = ProcessBuilder(commandString).directory(projectRootDirectory)

        return processBuilder.start().waitFor()
    }
}