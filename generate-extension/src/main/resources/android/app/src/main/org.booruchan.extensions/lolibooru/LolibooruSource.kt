package org.booruchan.extensions.lolibooru

import org.booruchan.extension.sdk.Source
import org.booruchan.extension.sdk.factory.AutocompleteSearchFactory
import org.booruchan.extension.sdk.factory.FetchPostsFactory
import org.booruchan.extension.sdk.factory.HealthCheckFactory
import org.booruchan.extension.sdk.settings.SourceSearchSettings
import org.booruchan.extension.sdk.settings.SourceSettings

class LolibooruSource : Source {

    override val id: String
        get() = "lolibooru"

    override val host: String
        get() = "https://lolibooru.moe"

    override val title: String
        get() = "Lolibooru"

    override val settings: SourceSettings
        get() = SourceSettings(
            searchSettings = SourceSearchSettings(
                initialPageNumber = 1,
            ),
        )

    override val healthCheckFactory: HealthCheckFactory
        get() = LolibooruHealthCheckFactory(
            host = host,
        )

    override val fetchPostsFactory: FetchPostsFactory
        get() = LolibooruFetchPostsFactory(
            host = host,
            fetchPostsParser = LolibooruFetchPostsParser(),
            sourceSettings = settings,
        )

    override val autocompleteSearchFactory: AutocompleteSearchFactory
        get() = LolibooruAutocompleteSearchFactory(
            host = host,
            autocompleteSearchParser = LolibooruAutocompleteSearchParser(),
            sourceSettings = settings,
        )

}
