package com.booruchan.buildsrc.task

import com.booruchan.buildsrc.Project
import com.booruchan.buildsrc.entity.CodegenContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
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

    @Input
    var androidTargetSdk: Int = Project.android.targetSdk

    @Input
    var androidMinSdk: Int = Project.android.minSdk

    @Input
    var androidCompileSdk: Int = Project.android.compileSdk

    @Input
    var versionName: String = "Undefined"

    private val versionCode: Int
        get() = versionName.filter { it.isDigit() }.toInt()

    @TaskAction
    fun invoke() {
        val context = CodegenContext(
            title = title,
            `package` = `package`,
            classname = classname,

            versionCode = versionCode,
            versionName = versionName,

            androidTargetSdk = androidTargetSdk,
            androidCompileSdk = androidCompileSdk,
            androidMinSdk = androidMinSdk,
        )
        outputFile.writeText(Json.encodeToString(context))
    }

}
