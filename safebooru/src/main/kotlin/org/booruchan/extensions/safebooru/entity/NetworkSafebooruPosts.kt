package org.booruchan.extensions.safebooru.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.booruchan.extension.sdk.entity.NetworkPost
import java.io.File

typealias NetworkSafebooruPosts = ArrayList<NetworkSafebooruPost>

@Serializable
data class NetworkSafebooruPost(
    @SerialName("id")
    override val id: Int,
    @SerialName("change")
    val change: Int,
    @SerialName("directory")
    val directory: String,
    @SerialName("hash")
    val hash: String,
    @SerialName("height")
    val height: Int,
    @SerialName("image")
    val image: String,
    @SerialName("owner")
    val owner: String,
    @SerialName("parent_id")
    val parentId: Int,
    @SerialName("rating")
    val rating: String,
    @SerialName("sample")
    val sample: Boolean,
    @SerialName("sample_height")
    val sampleHeight: Int,
    @SerialName("sample_width")
    val sampleWidth: Int,
    @SerialName("score")
    val score: Int?,
    @SerialName("tags")
    val tagsString: String,
    @SerialName("width")
    val width: Int,
) : NetworkPost {

    override val previewImageUrl: String
        get() = "https://safebooru.org/thumbnails/$directory/thumbnail_${File(image).nameWithoutExtension}.jpg"

    override val previewImageHeight: Int
        get() = sampleHeight

    override val previewImageWidth: Int
        get() = sampleWidth

    override val sampleImageUrl: String
        get() = "https://safebooru.org/samples/$directory/sample_${File(image).nameWithoutExtension}.jpg"

    override val sampleImageWidth: Int
        get() = width

    override val sampleImageHeight: Int
        get() = height

    override val tags: List<String>
        get() = tagsString.split(" ")
}
