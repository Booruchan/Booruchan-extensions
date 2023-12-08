package org.booruchan.extensions.generate.codegen

internal const val CurrentModuleTitle = "generate-extension"

/** Generates code that will be assembled into the ready to delivery library */
interface Codegen {
    operator fun invoke(context: CodegenContext)
}
