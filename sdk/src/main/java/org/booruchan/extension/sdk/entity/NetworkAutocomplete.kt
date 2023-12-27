package org.booruchan.extension.sdk.entity

/** Entity represents a single network tag for autocompletion */
interface NetworkAutocomplete {
    val title: String
    val value: String
    val count: Int
    /** Represents tag type, but can be empty if type is not defined */
    val type: NetworkTagType
}