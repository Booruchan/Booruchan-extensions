package org.booruchan.extension.sdk.factory

import org.booruchan.extension.sdk.entity.NetworkAutocomplete
import org.booruchan.extension.sdk.network.NetworkRequest
import org.booruchan.extension.sdk.network.NetworkResponse
import org.booruchan.extension.sdk.network.text
import org.booruchan.extension.sdk.parser.AutocompleteSearchParser
import org.booruchan.extension.sdk.settings.AutocompleteSearchSettings
import org.booruchan.extension.sdk.settings.SourceSettings

/** Factory for fetching possible completions of the typed tag. */
abstract class AutocompleteSearchFactory(
    private val autocompleteSearchParser: AutocompleteSearchParser,
    private val sourceSettings: SourceSettings,
) {
    /** All autocompletion settings stores here */
    open val settings: AutocompleteSearchSettings = AutocompleteSearchSettings(sourceSettings)

    /** Returns a Network request that should be executed by network client */
    abstract fun buildRequest(request: AutocompleteSearchRequest): NetworkRequest

    /** Encapsulates parsing network response into list of posts */
    open fun parseResponse(response: NetworkResponse): List<NetworkAutocomplete> {
        return autocompleteSearchParser.parse(response.content.text)
    }

    /** @param autocomplete query that should be autocompleted */
    data class AutocompleteSearchRequest(
        val autocomplete: String,
    )
}
