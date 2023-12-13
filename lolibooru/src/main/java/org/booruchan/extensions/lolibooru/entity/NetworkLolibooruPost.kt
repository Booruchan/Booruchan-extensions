package org.booruchan.extensions.lolibooru.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.booruchan.extension.sdk.entity.NetworkPost

typealias NetworkLolibooruPosts = ArrayList<NetworkLolibooruPost>

@Serializable
data class NetworkLolibooruPost(
    @SerialName("id")
    override val id: Int,
    @SerialName("tags")
    private val tagsString: String,
    @SerialName("preview_url")
    override val previewImageUrl: String,
    @SerialName("preview_height")
    override val previewImageHeight: Int,
    @SerialName("preview_width")
    override val previewImageWidth: Int,
    @SerialName("sample_url")
    override val sampleImageUrl: String,
    @SerialName("sample_height")
    override val sampleImageHeight: Int,
    @SerialName("sample_width")
    override val sampleImageWidth: Int,
) : NetworkPost {

    override val tags: List<String>
        get() = tagsString.split(" ")
}
