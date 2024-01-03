package org.booruchan.extension.sdk.settings

/** Settings for rating tag for booru source types */
data class SourceRatingTagSettings(

    /**
     * Values for "rating" tag (rating:safe, rating:explicit for example)
     * This values can be used for improving ux in searching.
     *
     * Note: Android app currently supports no more than first 3 items.
     * Other items will be ignored.
     * */
    val values: List<String> = emptyList(),

    /** Rating tag name that should be used for forming proper rating tag */
    val name: String = "rating",

    /** Separator for rating tag (rating:safe or rating:general) */
    val tagKeyValueSeparator: String = ":",
)