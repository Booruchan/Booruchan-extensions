package com.booruchan.buildsrc.entity

import kotlinx.serialization.Serializable

@Serializable
data class AndroidMetadataArtifactType(
    val kind: String,
    val type: String
)