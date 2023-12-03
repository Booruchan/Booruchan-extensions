package org.booruchan.extensions.generate.command

class WindowsGradleAssembleDebugCommand : GradleCommand() {
    override val gradle: String
        get() = "gradlew.bat"

    override val command: String
        get() = ":app:assembleDebug"
}