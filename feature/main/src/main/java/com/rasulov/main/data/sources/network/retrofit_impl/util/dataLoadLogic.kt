package com.rasulov.main.data.sources.network.retrofit_impl.util

import android.util.Log
import com.rasulov.feature.data.network.ConnectionException
import com.rasulov.feature.data.network.CacheHeader
import com.rasulov.feature.data.network.NOT_MODIFIED
import com.rasulov.feature.data.network.cache_info.NotOutdated
import com.rasulov.feature.data.network.cache_info.Revalidate
import com.rasulov.feature.data.network.cache_info.Status
import com.rasulov.feature.data.network.parseCacheRelatedHeaders
import com.rasulov.feature.data.network.parseHttpCodeToException
import retrofit2.Response
import java.io.IOException


inline fun <T> checkStatusAndLoadIfOutdated(
    getStatus: () -> Status,
    request: (Revalidate) -> Response<T>,
    updateCacheInfo: (CacheHeader) -> Unit,
    ifNotOutDated: () -> T
): T =
    when (val status = getStatus()) {
        is Revalidate -> revalidate(status, request, updateCacheInfo, ifNotOutDated)
        is NotOutdated -> ifNotOutDated()
    }

inline fun <T> revalidate(
    revalidate: Revalidate,
    request: (Revalidate) -> Response<T>,
    updateCacheInfo: (CacheHeader) -> Unit,
    ifNotOutDated: () -> T
): T = try {
    val response = request(revalidate)
    val code = response.code()

    val result = if (response.isSuccessful || code == NOT_MODIFIED) {
        val cacheHeader = response.parseCacheRelatedHeaders()
        updateCacheInfo(cacheHeader)
        response.body() ?: ifNotOutDated()
    } else throw parseHttpCodeToException(code, Throwable("Http response exception"))

    result

} catch (e: IOException) {
    throw ConnectionException(e)
}



