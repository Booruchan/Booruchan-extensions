package org.booruchan.extensions.generate.codegen

import java.io.File

data class CodegenContext(
    val root: File,
    val source: File,
    val `package`: String
)
