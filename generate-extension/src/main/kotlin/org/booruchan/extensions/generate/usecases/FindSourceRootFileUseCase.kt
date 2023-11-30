package org.booruchan.extensions.generate.usecases

import java.io.File

/** Returns a source code root */
interface FindSourceRootFileUseCase {
    operator fun invoke(sourceClassFile: File): File
}

class FindSourceRootFileUseCaseImpl : FindSourceRootFileUseCase {

    companion object {
        private val sources = listOf("java", "kotlin")
    }

    override fun invoke(sourceClassFile: File): File {
        var parentFile = sourceClassFile.parentFile
        while (!sources.contains(parentFile.name)) {
            parentFile = parentFile.parentFile
        }
        return parentFile
    }
}