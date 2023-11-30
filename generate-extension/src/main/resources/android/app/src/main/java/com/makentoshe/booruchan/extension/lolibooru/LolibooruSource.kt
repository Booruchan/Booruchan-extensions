package com.makentoshe.booruchan.extension.lolibooru

import org.booruchan.extension.sdk.Source
import org.booruchan.extension.sdk.factory.AutocompleteSearchFactory
import org.booruchan.extension.sdk.factory.FetchPostsFactory
import org.booruchan.extension.sdk.factory.HealthCheckFactory
import org.booruchan.extension.sdk.settings.SourceSettings
import org.booruchan.extensions.lolibooru.LolibooruSource

class LolibooruSource : Source {

    private val source = LolibooruSource()

    override val id: String
        get() = source.id

    override val host: String
        get() = source.host

    override val title: String
        get() = source.title

    override val settings: SourceSettings
        get() = source.settings

    override val healthCheckFactory: HealthCheckFactory
        get() = source.healthCheckFactory

    override val fetchPostsFactory: FetchPostsFactory
        get() = source.fetchPostsFactory

    override val autocompleteSearchFactory: AutocompleteSearchFactory
        get() = source.autocompleteSearchFactory

}
