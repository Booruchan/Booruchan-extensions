package task

import entity.CodegenContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

/** Creates a json file with context that will be provided to codegen tasks */
open class GenerateCodegenContext : DefaultTask() {

    @get:OutputFile
    val outputFile: File
        get() = File(project.buildDir, "${File.separator}templates${File.separator}codegen-context")

    @Input
    var `package`: String = project.group as String

    @Input
    var title: String = "Undefined"

    @Input
    var classname: String = "Undefined"

    @TaskAction
    fun invoke() {
        val context = CodegenContext(title = title, `package` = `package`, classname = classname)
        outputFile.writeText(Json.encodeToString(context))
    }

}
