package org.booruchan.extensions.generate.command

import java.io.File

abstract class GradleCommand() {

    abstract val gradle: String

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