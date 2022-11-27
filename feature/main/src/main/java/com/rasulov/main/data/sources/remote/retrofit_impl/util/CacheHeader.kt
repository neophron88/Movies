package com.rasulov.main.data.sources.remote.retrofit_impl.util

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