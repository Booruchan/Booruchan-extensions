package org.booruchan.extension.sdk.settings

data class SourceSettings(
    val searchSettings: SourceSearchSettings,
)

data class SourceSearchSettings(
    /** Initial page number for api. Mostly it is 0 but in some cases pagination might be started from other page */
    val initialPageNumber: Int = 0,
    /** How many posts will be requested per page. Default value is 30 */
    val requestedPostsPerPageCount: Int = 30,

    /** How search tags should be separated. Logical 'and' */
    val searchTagAnd: String = " ",
    /** How search tags should be negated, to exclude it from the search. Logical 'not' */
    val searchTagNot: String = "-",
    /** How search tags can be combined. Logical 'or' */
    val searchTagOr: String = "~"
)
