package org.booruchan.extension.sdk

import org.booruchan.extension.sdk.factory.AutocompleteSearchFactory
import org.booruchan.extension.sdk.factory.FetchPostFactory
import org.booruchan.extension.sdk.factory.FetchPostsFactory
import org.booruchan.extension.sdk.factory.FetchTagFactory
import org.booruchan.extension.sdk.factory.HealthCheckFactory
import org.booruchan.extension.sdk.settings.SourceSettings

interface Source {

    /** Should be unique for each source */
    val id: String

    /** Title how it will be displayed to user */
    val title: String

    /** Source base url */
    val host: String

    val settings: SourceSettings

    /** Network request factory for checking source availability */
    val healthCheckFactory: HealthCheckFactory? get() = null

    /** Network request and parsing providing factory for retrieving posts */
    val fetchPostsFactory: FetchPostsFactory? get() = null

    /** Network request and parsing providing factory for retrieving a single post */
    val fetchPostFactory: FetchPostFactory? get() = null

    /** Network request and parsing providing factory for autocompleting search */
    val autocompleteSearchFactory: AutocompleteSearchFactory? get() = null

    /** Network request and parsing providing factory for retrieving a single tag */
    val fetchTagFactory: FetchTagFactory? get() = null
}
