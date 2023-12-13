package org.booruchan.extension.sdk.factory

import org.booruchan.extension.sdk.entity.NetworkPost
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.network.NetworkResponse
import org.booruchan.extension.sdk.network.text
import org.booruchan.extension.sdk.parser.FetchPostsParser
import org.booruchan.extension.sdk.settings.FetchPostsSettings
import org.booruchan.extension.sdk.settings.SourceSettings

/** Factory for fetching posts. Each post represents image and some metadata for it */
abstract class FetchPostsFactory(
    private val fetchPostsParser: FetchPostsParser,
    private val sourceSettings: SourceSettings,
) {
    /** All search settings stores here */
    open val settings: FetchPostsSettings = FetchPostsSettings(sourceSettings)

    /** Initial page number for api. Mostly it is 0 but in some cases pagination might be started from other page */
    @Deprecated(message = "Deprecated. Use settings to access this field")
    open val initialPageNumber: Int = settings.initialPageNumber

    /** How many posts will be requested per page. Default value is 30 */
    @Deprecated(message = "Deprecated. Use settings to access this field")
    open val requestedPostsPerPageCount: Int = settings.requestedPostsPerPageCount

    /** How search tags should be separated */
    @Deprecated(message = "Deprecated. Use settings to access this field")
    open val searchTagSeparator: String = settings.searchSettings.searchTagAnd

    /** Returns a Network request that should be executed by network client */
    abstract fun buildRequest(request: FetchPostsRequest): NetworkRequest

    /** Encapsulates parsing network response into list of posts */
    open fun parseResponse(response: NetworkResponse): List<NetworkPost> {
        return fetchPostsParser.parse(response.content.text)
    }

    /**
     *  @param count how many posts we want to retrieve. There might be a hard limit on backend side
     *  for posts per request. The [requestedPostsPerPageCount] value can be used as a default value.
     *  @param page the page number for pagination. Pagination starts from the [initialPageNumber]
     *  @param tags the tags to search for. All tags should be separated by [searchTagSeparator] and
     *  joined into the string.
     *  */
    data class FetchPostsRequest(
        val count: Int,
        val page: Int,
        val tags: String,
    )
}
