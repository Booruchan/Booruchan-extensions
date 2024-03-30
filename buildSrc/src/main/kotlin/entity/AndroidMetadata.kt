package entity

import kotlinx.serialization.Serializable

@Serializable
data class AndroidMetadata(
    val applicationId: String,
    val artifactType: AndroidMetadataArtifactType,
    val elementType: String,
    val elements: List<AndroidMetadataElement>,
    val variantName: String,
    val version: Int
)