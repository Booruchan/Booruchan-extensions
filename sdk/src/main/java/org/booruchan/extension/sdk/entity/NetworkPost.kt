package org.booruchan.extension.sdk.entity

/** Entity represents a single network post (image with some metadata, etc) */
interface NetworkPost {
    val id: Int
    val tags: List<String>

    val previewImageUrl: String
    val previewImageWidth: Int
    val previewImageHeight: Int

    val sampleImageUrl: String
    val sampleImageWidth: Int
    val sampleImageHeight: Int
}
