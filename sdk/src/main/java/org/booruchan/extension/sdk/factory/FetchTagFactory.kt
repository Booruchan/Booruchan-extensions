package org.booruchan.extension.sdk.factory

import org.booruchan.extension.sdk.entity.NetworkTag
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.network.NetworkResponse
import org.booruchan.extension.sdk.network.text
import org.booruchan.extension.sdk.parser.FetchTagParser
import org.booruchan.extension.sdk.settings.SourceSettings

abstract class FetchTagFactory(
    private val fetchTagParser: FetchTagParser,
    private val sourceSettings: SourceSettings,
) {

    /** Returns a Network request that should be executed by network client */
    abstract fun buildRequest(request: FetchTagFactory.FetchTagRequest): NetworkRequest

    /** Encapsulates parsing network response into list of posts */
    open fun parseResponse(response: NetworkResponse): List<NetworkTag> {
        return fetchTagParser.parse(response.content.text)
    }

    sealed class FetchTagRequest {
        abstract val id: String
        abstract val name: String

        data class Id(override val id: String): FetchTagRequest() {
            override val name: String = ""
        }

        data class Name(override val name: String): FetchTagRequest() {
            override val id: String = ""
        }
    }
}