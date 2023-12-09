package org.booruchan.extensions.generate.command

import org.booruchan.extensions.generate.logger.Logger

/** Invokes gradlew :app:assembleDebug command for Windows OS */
class WindowsGradleAssembleDebugCommand(logger: Logger) : GradleCommand(logger) {
    override val gradle: String
        get() = "gradlew.bat"

    override val command: String
        get() = ":app:assembleDebug"
}