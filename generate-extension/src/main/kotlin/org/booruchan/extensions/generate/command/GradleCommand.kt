package org.booruchan.extensions.generate.command

import java.io.File

/** Command that can be invoked on generated gradle project */
abstract class GradleCommand() {

    /** Gradle invocation. Can be different for OS */
    abstract val gradle: String

    /** Command that will be invoked */
    abstract val command: String

    operator fun invoke(projectRootDirectory: File) {
        val processBuilder = ProcessBuilder("$gradle $command".split(" "))
            .directory(projectRootDirectory)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)

        val process = processBuilder.start()
        val exitCode = process.waitFor()

        if (exitCode == 0) {
            println("Build successful")
        } else {
            println("Build failed with exit code: $exitCode")
        }
    }
}