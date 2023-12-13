package org.booruchan.extension.sdk.settings

data class AutocompleteSearchSettings internal constructor(
    /** How search tags should be separated */
    val searchTagAnd: String,
    /** How search tags should be negated, to exclude it from the search */
    val searchTagNot: String,
    /** How search tags should be combined */
    val searchTagOr: String,
) {
    constructor(sourceSettings: SourceSettings) : this(
        searchTagAnd = sourceSettings.searchSettings.searchTagAnd,
        searchTagNot = sourceSettings.searchSettings.searchTagNot,
        searchTagOr = sourceSettings.searchSettings.searchTagOr,
    )
}