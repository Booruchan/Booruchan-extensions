package org.booruchan.extensions.safebooru

import org.booruchan.extension.sdk.factory.HealthCheckFactory
import org.booruchan.extension.sdk.network.NetworkMethod
import org.booruchan.extension.sdk.network.NetworkRequest

class SafebooruHealthCheckFactory(
    private val host: String,
) : HealthCheckFactory() {
    override fun buildRequest(): NetworkRequest {
        return NetworkRequest(method = NetworkMethod.Head, url = host)
    }
}