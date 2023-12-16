package org.booruchan.extensions.safebooru

import org.booruchan.extension.sdk.entity.NetworkTag
import org.booruchan.extension.sdk.factory.FetchTagFactory
import org.booruchan.extension.sdk.network.NetworkMethod
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.parser.FetchTagParser
import org.booruchan.extension.sdk.settings.SourceSettings
import org.booruchan.extensions.safebooru.entity.NetworkSafebooruTag
import org.jsoup.Jsoup

class SafebooruFetchTagFactory(
    private val host: String,
    fetchTagParser: FetchTagParser,
    sourceSettings: SourceSettings,
) : FetchTagFactory(fetchTagParser, sourceSettings) {
    override fun buildRequest(request: FetchTagRequest): NetworkRequest {
        return NetworkRequest(
            method = NetworkMethod.Get,
            url = host,
            parameters = mapOf(
                "page" to "dapi",
                "s" to "tag",
                "q" to "index",

                // json is disabled for this request
                //"json" to "1", // force responding with json instead of xml

                "id" to request.id,
                "name" to request.name
            )
        )
    }
}

class SafebooruFetchTagParser : FetchTagParser {
    override fun parse(string: String): List<NetworkTag> {
        val body = Jsoup.parse(string).body()
        val tags = body.select("tag")
        return tags.map { tag ->
            NetworkSafebooruTag(
                id = tag.attr("id"),
                value = tag.attr("name"),
                count = tag.attr("count").toIntOrNull() ?: 0,
                ambiguous = tag.attr("ambiguous").toBoolean(),
                type = tag.attr("type"),
            )
        }
    }
}
