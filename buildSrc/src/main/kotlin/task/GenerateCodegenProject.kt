package task

import com.samskivert.mustache.Mustache
import entity.CodegenContext
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

/** Generates a project that is ready to be built by gradle system */
open class GenerateCodegenProject : DefaultTask() {

    companion object {
        private const val SourceTitleName = "SourceTitle"
        private const val SourcePackageName = "SourcePackage"
        private const val SourceClassName = "SourceClassName"

        private const val ProjectAndroidCompileSdk = "ProjectAndroidCompileSdk"
        private const val ProjectAndroidTargetSdk = "ProjectAndroidTargetSdk"
        private const val ProjectAndroidMinSdk = "ProjectAndroidMinSdk"
    }

    private val mustacheCompiler: Mustache.Compiler = Mustache.compiler()

    @Input
    var type: Type = Type.Undefined

    @get:InputFile
    val inputFile: File
        get() = File(project.buildDir, "${File.separator}templates${File.separator}codegen-context")

    @get:OutputDirectory
    val outputDirectory: File
        get() = File(project.buildDir, "${File.separator}templates${File.separator}${type}".lowercase())

    @TaskAction
    fun codegen() {
        val context = Json.decodeFromString<CodegenContext>(inputFile.readText())

        when (type) {
            Type.Android -> androidCodegen(context)
            Type.Undefined -> {
                logger.debug("Undefined codegen type. Skip this module.")
            }
        }
    }

    private fun androidCodegen(context: CodegenContext) {
        val resourcesDirectory =
            File(project.rootDir, "buildSrc${File.separator}src${File.separator}main${File.separator}resources")

        // Create project and inflate template files
        val androidProjectDirectory = File(resourcesDirectory, "android")
        androidProjectDirectory.walkTopDown().forEach { file ->
            if (!file.isFile) return@forEach
            val destination = File(outputDirectory, file.path.removePrefix(androidProjectDirectory.path))
            // If mustache inflate file from template, copy otherwise
            if (file.extension == "mustache") {
                inflateMustacheTemplate(context, file, destination)
            } else {
                file.copyTo(destination, overwrite = true)
            }
        }

        // Copy files from the src
        val mainDirectory = File(project.projectDir, "src${File.separator}main")
        if (!mainDirectory.exists()) {
            throw IllegalStateException("Could not locate ${project.name} source files: kotlin or java")
        }

        mainDirectory.walkTopDown().forEach { file ->
            if (!file.isFile) return@forEach
            val destination = File(File(outputDirectory, "app"), file.path.removePrefix(project.projectDir.path))
            file.copyTo(destination, overwrite = true)
        }
    }

    private fun inflateMustacheTemplate(context: CodegenContext, file: File, destination: File) {
        val destination = File(destination.path.removeSuffix(".mustache"))

        if (destination.exists()) destination.delete()
        if (!destination.parentFile.exists()) destination.parentFile.mkdirs()
        if (!destination.createNewFile()) throw IllegalStateException("Couldn't create $destination")

        val template = mustacheCompiler.escapeHTML(false).emptyStringIsFalse(true).compile(file.readText())
        val content = template.execute(buildArguments(context))

        destination.writeText(content)
    }

    private fun buildArguments(context: CodegenContext) = HashMap<String, String>().apply {
        put(SourceTitleName, context.title)
        put(SourcePackageName, context.`package`)
        put(SourceClassName, context.classname)
        put(ProjectAndroidMinSdk, context.androidMinSdk.toString())
        put(ProjectAndroidCompileSdk, context.androidCompileSdk.toString())
        put(ProjectAndroidTargetSdk, context.androidTargetSdk.toString())
    }

    enum class Type {
        Undefined, Android,
    }
}

