package com.booruchan.buildsrc.entity

import kotlinx.serialization.Serializable

@Serializable
data class CodegenContext(
    val title: String,
    val `package`: String,
    val classname: String,

    val versionCode: Int,
    val versionName: String,

    val androidMinSdk: Int,
    val androidTargetSdk: Int,
    val androidCompileSdk: Int,
)
