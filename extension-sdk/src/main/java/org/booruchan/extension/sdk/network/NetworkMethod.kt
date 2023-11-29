package org.booruchan.extension.sdk.network

/** Network method will be mapped to the method for the network implementation library */
sealed interface NetworkMethod {
    object Get: NetworkMethod
    object Head: NetworkMethod
}