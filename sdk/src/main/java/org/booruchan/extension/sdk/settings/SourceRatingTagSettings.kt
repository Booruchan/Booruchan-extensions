package org.booruchan.extension.sdk.settings

/** Settings for rating metadata tag for booru source types */
data class SourceRatingTagSettings(
    /**
     * Values for "rating" meta tag (rating:safe, rating:explicit for example)
     * This values can be used for improving ux in searching
     * */
    val values: List<String> = emptyList(),
    /** Rating tag name that should be used for forming proper rating tag */
    val name: String = "rating",
    /** Separator for rating tag (rating:safe or rating:general) */
    val tagKeyValueSeparator: String = ":",
)