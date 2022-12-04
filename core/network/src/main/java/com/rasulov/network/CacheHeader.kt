package com.rasulov.network

import retrofit2.Response

data class CacheHeader(
    val maxAge: Int?,
    val eTag: String?
)

fun Response<*>.parseCacheRelatedHeaders(): CacheHeader {
    val headers = headers()
    val maxAge = headers["Cache-Control"]?.substringAfter("=")
    val eTag = headers["ETag"]
    return CacheHeader(maxAge?.toIntOrNull(), eTag)
}