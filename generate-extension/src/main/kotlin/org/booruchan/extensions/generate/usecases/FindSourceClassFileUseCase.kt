package org.booruchan.extensions.generate.usecases

import java.io.File

interface FindSourceClassFileUseCase {
    operator fun invoke(projectModuleFile: File): File?
}

class FindSourceClassFileUseCaseRegexp : FindSourceClassFileUseCase {

    private val regexPattern = """\bclass\s+\w+\s+.*:\s*Source\s*""".toRegex()

    override fun invoke(projectModuleFile: File): File? {
        return projectModuleFile.walkBottomUp().filter { it.isFile }.filter {
            // walk in the fs tree and skip all files in "build", "test" and "resources" directories
            !it.path.contains("build") && !it.path.contains("test") && !it.path.contains("resources")
        }.filter { kotlinFile ->
            // Find class inheriting from Source line in file
            regexPattern.find(kotlinFile.readText()) != null
        }.firstOrNull()
    }
}
