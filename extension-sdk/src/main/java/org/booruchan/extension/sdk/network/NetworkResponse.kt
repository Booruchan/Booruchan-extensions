package org.booruchan.extension.sdk.network

data class NetworkResponse(
    val request: NetworkRequest,
    val statusCode: Int,
    val content: NetworkContent,
)

@Suppress("ArrayInDataClass")
data class NetworkContent(val bytes: ByteArray)

val NetworkContent.text: String get() = String(bytes)