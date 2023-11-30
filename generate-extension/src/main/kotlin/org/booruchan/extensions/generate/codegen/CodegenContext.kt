package org.booruchan.extensions.generate.codegen

import java.io.File

data class CodegenContext(
    val moduleRootDirectory: File,
    /** Source interface implementation file */
    val sourceClassDirectory: File,
    /** Source package that will be applied to extension */
    val sourcePackage: String,
    /** Source title */
    val sourceTitle: String,
    /** Name of the class where Source interface implemented*/
    val sourceClassName: String,

    val sourceId: String,
)
