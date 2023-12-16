package org.booruchan.extensions.safebooru.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.booruchan.extension.sdk.entity.NetworkTag

@Serializable
data class NetworkSafebooruTag(
    @SerialName("id")
    override val id: String,
    @SerialName("count")
    override val count: Int,
    @SerialName("name")
    override val value: String,
    @SerialName("ambiguous")
    val ambiguous: Boolean,
    @SerialName("type")
    val type: String,
) : NetworkTag