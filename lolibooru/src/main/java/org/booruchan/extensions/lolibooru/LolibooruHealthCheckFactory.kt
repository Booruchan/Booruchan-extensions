package org.booruchan.extensions.lolibooru

import org.booruchan.extension.sdk.factory.HealthCheckFactory
import org.booruchan.extension.sdk.network.NetworkMethod
import org.booruchan.extension.sdk.network.NetworkRequest

class LolibooruHealthCheckFactory(
    private val host: String,
) : HealthCheckFactory() {
    override fun buildRequest(): NetworkRequest {
        return NetworkRequest(method = NetworkMethod.Head, url = host)
    }
}