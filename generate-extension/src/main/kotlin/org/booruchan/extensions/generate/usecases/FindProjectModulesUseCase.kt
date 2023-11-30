package org.booruchan.extensions.generate.usecases

import java.io.File

/** Returns all project modules */
interface FindProjectModulesUseCase {
    operator fun invoke(projectDirectory: File): List<File>
}

class FindProjectModulesUseCaseImpl: FindProjectModulesUseCase {
    override fun invoke(projectDirectory: File): List<File> {
        return projectDirectory.listFiles()?.filter { projectFile ->
            projectFile.isDirectory
        }?.filter { projectFile ->
            projectFile.list()?.any { it.contains("build.gradle") } ?: false
        } ?: emptyList()
    }
}
