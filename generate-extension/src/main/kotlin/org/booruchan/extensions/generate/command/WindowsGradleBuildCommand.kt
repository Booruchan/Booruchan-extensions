package org.booruchan.extensions.generate.command

/** Invokes gradlew build command for Windows OS */
class WindowsGradleBuildCommand : GradleCommand() {
    override val gradle: String
        get() = "gradlew.bat"
    override val command: String
        get() = "build"
}

