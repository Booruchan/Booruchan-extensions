package org.booruchan.extension.sdk.settings

data class FetchPostsSettings internal constructor(
    /** Initial page number for api. Mostly it is 0 but in some cases pagination might be started from other page */
    val initialPageNumber: Int,
    /** How many posts will be requested per page. Default value is 30 */
    val requestedPostsPerPageCount: Int,
    /** Contains settings, several predefined tags and their possible values for enhance ui */
    val searchSettings: FetchPostsSearchSettings,
) {
    constructor(sourceSettings: SourceSettings) : this(
        initialPageNumber = sourceSettings.searchSettings.initialPageNumber,
        requestedPostsPerPageCount = sourceSettings.searchSettings.requestedPostsPerPageCount,
        searchSettings = FetchPostsSearchSettings(
            searchTagAnd = sourceSettings.searchSettings.searchTagAnd,
            searchTagNot = sourceSettings.searchSettings.searchTagNot,
            searchTagOr = sourceSettings.searchSettings.searchTagOr,
        )
    )
}

data class FetchPostsSearchSettings(
    /** How search tags should be separated. Logical 'and' */
    val searchTagAnd: String = " ",
    /** How search tags should be negated, to exclude it from the search. Logical 'not' */
    val searchTagNot: String = "-",
    /** How search tags can be combined. Logical 'or' */
    val searchTagOr: String = "~"
)
