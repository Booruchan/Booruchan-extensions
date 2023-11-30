package org.booruchan.extensions.lolibooru.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.booruchan.extension.sdk.entity.NetworkAutocomplete

typealias NetworkLolibooruAutocompletes = ArrayList<NetworkLolibooruAutocomplete>

//"id": "452",
//"name": "hair_flower",
//"post_count": "22769",
//"cached_related": "hair_flower,22638,hair_ornament,22422,flower,22138,highres,16730,1girl,14752,long_hair,13318,blush,11954,looking_at_viewer,11713,solo,11065,smile,10338,open_mouth,10093,bangs,8976,breasts,7721,multiple_girls,7333,dress,6824,hair_between_eyes,6532,blue_eyes,6393,absurdres,6353,short_hair,6278,long_sleeves,6170,blonde_hair,5893,brown_hair,5030,simple_background,4965,eyebrows_visible_through_hair,4881,animal_ears,4830",
//"cached_related_expires_on": "2024-09-10 00:53:57",
//"tag_type": 0,
//"is_ambiguous": false
@Serializable
data class NetworkLolibooruAutocomplete(
    @SerialName("name") override val value: String,
    @SerialName("post_count") override val count: Int,
): NetworkAutocomplete {

    override val title: String
        get() = value.replace("_", " ")

}
