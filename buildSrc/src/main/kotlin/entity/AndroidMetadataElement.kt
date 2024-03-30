package entity

import kotlinx.serialization.Serializable

@Serializable
data class AndroidMetadataElement(
//    val attributes: List<Any>,
//    val filters: List<Any>,
    val outputFile: String,
    val type: String,
    val versionCode: Int,
    val versionName: String
)