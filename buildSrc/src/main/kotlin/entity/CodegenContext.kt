package entity

import kotlinx.serialization.Serializable

@Serializable
data class CodegenContext(
    val title: String,
    val `package`: String,
    val classname: String,

    val androidMinSdk: Int,
    val androidTargetSdk: Int,
    val androidCompileSdk: Int,
)
