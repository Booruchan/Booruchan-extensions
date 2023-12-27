package org.booruchan.extensions.safebooru.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.booruchan.extension.sdk.entity.NetworkAutocomplete
import org.booruchan.extension.sdk.entity.NetworkTagType

typealias NetworkSafebooruAutocompletes = ArrayList<NetworkSafebooruAutocomplete>

// {"label":"hat (674552)","value":"hat"}
@Serializable
data class NetworkSafebooruAutocomplete(
    @SerialName("label") val label: String,
    @SerialName("value") override val value: String,
) : NetworkAutocomplete {

    override val type: NetworkTagType
        get() = NetworkTagType.Other

    override val title: String
        get() = label.split(" ")[0]

    override val count: Int
        get() = label.split(" ")[1].run { substring(1, lastIndex) }.toInt()
}
