package com.booruchan.buildsrc.entity

import kotlinx.serialization.Serializable

@Serializable
data class AndroidDeployMetadata(
    val packages: List<AndroidDeployPackage>,
)
