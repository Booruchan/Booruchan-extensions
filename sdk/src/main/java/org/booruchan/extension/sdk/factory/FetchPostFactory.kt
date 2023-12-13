package org.booruchan.extension.sdk.factory

import org.booruchan.extension.sdk.entity.NetworkPost
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.network.NetworkResponse
import org.booruchan.extension.sdk.network.text
import org.booruchan.extension.sdk.parser.FetchPostParser
import org.booruchan.extension.sdk.settings.SourceSettings

abstract class FetchPostFactory(
    private val fetchPostsParser: FetchPostParser,
    private val sourceSettings: SourceSettings,
) {

    /** Returns a Network request that should be executed by network client */
    abstract fun buildRequest(request: FetchPostRequest): NetworkRequest

    /** Encapsulates parsing network response into a post */
    open fun parseResponse(response: NetworkResponse): NetworkPost {
        return fetchPostsParser.parse(response.content.text)
    }

    data class FetchPostRequest(
        val id: String,
    )
}