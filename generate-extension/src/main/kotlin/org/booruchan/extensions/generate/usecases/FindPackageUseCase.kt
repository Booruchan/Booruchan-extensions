package org.booruchan.extensions.generate.usecases

import java.io.File

interface FindPackageUseCase {
    operator fun invoke(sourceClassFile: File): String

}

class FindPackageUseCaseImpl(
    private val findSourceRootFile: FindSourceRootFileUseCase,
): FindPackageUseCase {
    override operator fun invoke(sourceClassFile: File): String {
        // Find source code root where packages stored, like "java", "kotlin", "resources" etc.
        val moduleCodeRootFile = findSourceRootFile(sourceClassFile)
        // Remove root path from source class path => we're receiving package with path separators
        val packagePath = sourceClassFile.absolutePath.removePrefix(moduleCodeRootFile.absolutePath)
        // Drop first separator, filename with extension and replace all other separators with dots
        return packagePath.drop(1).dropLastWhile {
            it != File.separatorChar
        }.replace(File.separator, ".").dropLast(1)
    }
}
