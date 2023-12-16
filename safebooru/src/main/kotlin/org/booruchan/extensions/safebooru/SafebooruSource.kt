package org.booruchan.extensions.safebooru

import org.booruchan.extension.sdk.Source
import org.booruchan.extension.sdk.factory.AutocompleteSearchFactory
import org.booruchan.extension.sdk.factory.FetchPostsFactory
import org.booruchan.extension.sdk.factory.FetchTagFactory
import org.booruchan.extension.sdk.factory.HealthCheckFactory
import org.booruchan.extension.sdk.settings.SourceSearchSettings
import org.booruchan.extension.sdk.settings.SourceSettings

class SafebooruSource : Source {

    override val id: String
        get() = "safebooru"

    override val host: String
        get() = "https://safebooru.org"

    override val title: String
        get() = "Safebooru"

    override val settings: SourceSettings
        get() = SourceSettings(
            searchSettings = SourceSearchSettings(),
        )

    override val healthCheckFactory: HealthCheckFactory
        get() = SafebooruHealthCheckFactory(
            host = host,
        )

    override val fetchPostsFactory: FetchPostsFactory
        get() = SafebooruFetchPostsFactory(
            host = host,
            sourceSettings = settings,
            fetchPostsParser = SafebooruFetchPostsParser(),
        )

    override val autocompleteSearchFactory: AutocompleteSearchFactory
        get() = SafebooruAutocompleteSearchFactory(
            host = host,
            sourceSettings = settings,
            autocompleteSearchParser = SafebooruAutocompleteSearchParser(),
        )

    override val fetchTagFactory: FetchTagFactory
        get() = SafebooruFetchTagFactory(
            host = host,
            sourceSettings = settings,
            fetchTagParser = SafebooruFetchTagParser(),
        )

}
