package org.booruchan.extensions.generate.codegen

internal const val CurrentModuleTitle = "generate-extension"

interface Codegen {
    operator fun invoke(context: CodegenContext)
}

