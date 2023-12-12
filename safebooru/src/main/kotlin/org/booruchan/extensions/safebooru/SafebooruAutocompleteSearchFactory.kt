package org.booruchan.extensions.safebooru

import kotlinx.serialization.json.Json
import org.booruchan.extension.sdk.entity.NetworkAutocomplete
import org.booruchan.extension.sdk.factory.AutocompleteSearchFactory
import org.booruchan.extension.sdk.network.NetworkMethod
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.parser.AutocompleteSearchParser
import org.booruchan.extension.sdk.settings.SourceSettings
import org.booruchan.extensions.safebooru.entity.NetworkSafebooruAutocompletes

class SafebooruAutocompleteSearchFactory(
    private val host: String,
    autocompleteSearchParser: AutocompleteSearchParser,
    sourceSettings: SourceSettings,
) : AutocompleteSearchFactory(autocompleteSearchParser, sourceSettings) {
    override fun buildRequest(request: AutocompleteSearchRequest): NetworkRequest {
        return NetworkRequest(
            method = NetworkMethod.Get,
            url = host.plus("/autocomplete.php"),
            parameters = mapOf(
                "q" to request.autocomplete,
            )
        )
    }
}

class SafebooruAutocompleteSearchParser : AutocompleteSearchParser {
    override fun parse(string: String): List<NetworkAutocomplete> {
        return Json.decodeFromString<NetworkSafebooruAutocompletes>(string)
    }
}
