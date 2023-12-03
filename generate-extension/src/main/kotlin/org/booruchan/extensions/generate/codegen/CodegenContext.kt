package org.booruchan.extensions.generate.codegen

import java.io.File

data class CodegenContext(
    /** Source interface implementation file */
    val sourceClassFile: File,
    /** Main 'java' or 'kotlin' directory where packages will be placed */
    val moduleRootDirectory: File,
    /** 'build' directory for the specified source file */
    val buildDirectory: File,

    /** Source package that will be applied to extension */
    val sourcePackage: String,

    /** Source title */
    val sourceTitle: String,
    /** Name of the class where Source interface implemented*/
    val sourceClassName: String,

    val sourceId: String,
)
