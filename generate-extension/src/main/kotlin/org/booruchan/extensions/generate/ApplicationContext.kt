package org.booruchan.extensions.generate

data class ApplicationContext(
    val command: ApplicationCommand = ApplicationCommand.Undefined,
    val extension: ApplicationExtensions = ApplicationExtensions.All,
)


sealed interface ApplicationExtensions {
    object All: ApplicationExtensions

    data class Exact(val id: String): ApplicationExtensions
}

sealed interface ApplicationCommand {
    object Undefined: ApplicationCommand
    object AssembleDebug: ApplicationCommand
}
