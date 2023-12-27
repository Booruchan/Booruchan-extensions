package org.booruchan.extensions.safebooru

import kotlinx.serialization.json.Json
import org.booruchan.extension.sdk.entity.NetworkPost
import org.booruchan.extension.sdk.factory.FetchPostsFactory
import org.booruchan.extension.sdk.network.NetworkMethod
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.parser.FetchPostsParser
import org.booruchan.extension.sdk.settings.SourceSettings
import org.booruchan.extensions.safebooru.entity.NetworkSafebooruPosts

class SafebooruFetchPostsFactory(
    private val host: String,
    fetchPostsParser: FetchPostsParser,
    sourceSettings: SourceSettings,
) : FetchPostsFactory(fetchPostsParser, sourceSettings) {
    override fun buildRequest(request: FetchPostsRequest): NetworkRequest {
        return NetworkRequest(
            method = NetworkMethod.Get,
            url = host,
            parameters = mapOf(
                "page" to "dapi",
                "s" to "post",
                "q" to "index",
                "json" to "1", // force responding with json instead of xml

                "limit" to request.count.toString(),
                "pid" to request.page.toString(),
                "tags" to request.tags,
            )
        )
    }
}

class SafebooruFetchPostsParser : FetchPostsParser {

    private val json = Json

    override fun parse(string: String): List<NetworkPost> {
        return json.decodeFromString<NetworkSafebooruPosts>(string)
    }
}
