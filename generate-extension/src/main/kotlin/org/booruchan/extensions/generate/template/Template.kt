package org.booruchan.extensions.generate.template

import com.samskivert.mustache.Mustache
import org.booruchan.extensions.generate.codegen.CodegenContext
import java.io.File


interface TemplateInflater {
    fun inflate(context: CodegenContext, templateFile: File, destination: File)
}

class MustacheTemplateInflater(
    private val mustacheCompiler: Mustache.Compiler = Mustache.compiler(),
) : TemplateInflater {

    override fun inflate(context: CodegenContext, templateFile: File, destination: File) {
        val destination = File(destination.path.removeSuffix(".mustache"))

        val template = mustacheCompiler.escapeHTML(false).emptyStringIsFalse(true).compile(templateFile.readText())
        val content = template.execute(buildArguments(context))

        if (destination.exists()) destination.delete()
        if (!destination.parentFile.exists()) destination.parentFile.mkdirs()
        if (!destination.createNewFile()) return println("Couldn't create $destination")

        destination.writeText(content)
    }

    private fun buildArguments(context: CodegenContext) = HashMap<String, String>().apply {
        put(SourceTitleName, context.sourceTitle)
        put(SourcePackageName, context.sourcePackage)
        put(SourceClassName, context.sourceClassName)
    }

    companion object {
        private const val SourceTitleName = "SourceTitle"
        private const val SourcePackageName = "SourcePackage"
        private const val SourceClassName = "SourceClassName"
    }
}