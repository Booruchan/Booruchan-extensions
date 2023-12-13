package org.booruchan.extension.sdk.factory

import org.booruchan.extension.sdk.network.NetworkRequest

/** Factory for head request for checking availability */
abstract class HealthCheckFactory {

    /** Returns a Network request that should be executed by network client */
    abstract fun buildRequest(): NetworkRequest
}