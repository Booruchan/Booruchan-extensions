package org.booruchan.extensions.generate.command

class WindowsGradleBuildCommand : GradleCommand() {
    override val gradle: String
        get() = "gradlew.bat"
    override val command: String
        get() = "build"
}

