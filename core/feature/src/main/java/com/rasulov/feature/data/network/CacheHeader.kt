package com.rasulov.feature.data.network

import android.util.Log
import retrofit2.Response

data class CacheHeader(
    val maxAge: Int?,
    val eTag: String?
)

fun Response<*>.parseCacheRelatedHeaders(): CacheHeader {
    val headers = headers()
    val maxAge = findMaxAge(headers["Cache-Control"])
    val eTag = headers["ETag"]
    Log.d("it0088", "parseCacheRelatedHeaders: $maxAge")
    return CacheHeader(maxAge, eTag)
}

private fun findMaxAge(string: String?): Int? =
    string?.let { str ->
        val found = str.split(",").firstOrNull { it.contains("max-age") }?.trim()
        found?.substringAfter("=")
    }?.toIntOrNull()