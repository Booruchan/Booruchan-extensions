package org.booruchan.extensions.generate.command

import org.booruchan.extensions.generate.logger.Logger
import java.io.File

/** Command that can be invoked on generated gradle project */
abstract class GradleCommand(private val logger: Logger) {

    /** Gradle invocation. Can be different for OS */
    abstract val gradlew: String

    /** Command that will be invoked */
    abstract val command: String

    operator fun invoke(projectRootDirectory: File): Int {
        val gradlewAbsolutePath = projectRootDirectory.listFiles()?.find { it.name == gradlew }?.absolutePath
        val commandString = "$gradlewAbsolutePath $command".split(" ")

        val processBuilder = ProcessBuilder(commandString).directory(projectRootDirectory)

        return processBuilder.start().waitFor()
    }
}