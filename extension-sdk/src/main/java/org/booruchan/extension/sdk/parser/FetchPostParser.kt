package org.booruchan.extension.sdk.parser

import org.booruchan.extension.sdk.entity.NetworkPost
import org.booruchan.extension.sdk.factory.FetchPostFactory
import org.booruchan.extension.sdk.network.NetworkResponse

/**
 * Represents how [NetworkResponse] from [FetchPostFactory] will be parsed.
 *
 * There are several schemes that can be returned. The default one is Json,
 * but some services may return Xml, so we want to be ready to implement
 * own parser for each source factory.
 * */
interface FetchPostParser {
    /** Parse input string from the network response into list of posts that were requested */
    fun parse(string: String): NetworkPost
}
