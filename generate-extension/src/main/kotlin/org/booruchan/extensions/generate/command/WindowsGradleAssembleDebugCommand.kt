package org.booruchan.extensions.generate.command

/** Invokes gradlew :app:assembleDebug command for Windows OS */
class WindowsGradleAssembleDebugCommand : GradleCommand() {
    override val gradle: String
        get() = "gradlew.bat"

    override val command: String
        get() = ":app:assembleDebug"
}