package org.booruchan.extensions.lolibooru

import kotlinx.serialization.json.Json
import org.booruchan.extension.sdk.entity.NetworkPost
import org.booruchan.extension.sdk.factory.FetchPostsFactory
import org.booruchan.extension.sdk.network.NetworkMethod
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.parser.FetchPostsParser
import org.booruchan.extension.sdk.settings.SourceSettings
import org.booruchan.extensions.lolibooru.entity.NetworkLolibooruPosts

class LolibooruFetchPostsFactory(
    private val host: String,
    fetchPostsParser: FetchPostsParser,
    sourceSettings: SourceSettings,
) : FetchPostsFactory(
    fetchPostsParser = fetchPostsParser,
    sourceSettings = sourceSettings,
) {
    override fun buildRequest(request: FetchPostsRequest): NetworkRequest {
        return NetworkRequest(
            method = NetworkMethod.Get,
            url = "$host/post/index.json",
            parameters = mapOf(
                "limit" to request.count.toString(),
                "page" to request.page.toString(),
                "tags" to request.tags,
            )
        )
    }
}

class LolibooruFetchPostsParser : FetchPostsParser {

    private val json = Json { ignoreUnknownKeys = true }

    override fun parse(string: String): List<NetworkPost> {
        return json.decodeFromString<NetworkLolibooruPosts>(string)
    }
}
