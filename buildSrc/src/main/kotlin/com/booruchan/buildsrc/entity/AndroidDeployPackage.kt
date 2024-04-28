package com.booruchan.buildsrc.entity

import kotlinx.serialization.Serializable

@Serializable
data class AndroidDeployPackage(
    val applicationId: String,
    val versionCode: Int,
    val versionName: String,
    val apkFilename: String,
)
