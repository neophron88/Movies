package com.rasulov.main.data.network_datasource.retrofit_impl.util

import com.rasulov.feature.data.base.networkdatasource.*
import com.rasulov.main.data.network_datasource.cache_info.NotOutdated
import com.rasulov.main.data.network_datasource.cache_info.Revalidate
import com.rasulov.main.data.network_datasource.cache_info.Status
import com.rasulov.network.*
import retrofit2.Response
import java.io.IOException


inline fun <T> checkStatusAndLoadIfOutdated(
    getStatus: () -> Status,
    request: (Revalidate) -> Response<T>,
    updateCacheInfo: (CacheHeader) -> Unit,
    ifNotOutDated: () -> T
): T = when (val status = getStatus()) {
    is Revalidate -> revalidate(status, request, updateCacheInfo, ifNotOutDated)
    is NotOutdated -> ifNotOutDated()


}

inline fun <T> revalidate(
    status: Revalidate,
    request: (Revalidate) -> Response<T>,
    updateCacheInfo: (CacheHeader) -> Unit,
    ifNotOutDated: () -> T
): T = try {
    val response = request(status)
    val code = response.code()
    if (response.isSuccessful || code == NOT_MODIFIED) {
        val cacheHeader = response.parseCacheRelatedHeaders()
        updateCacheInfo(cacheHeader)
        response.body() ?: ifNotOutDated()
    }

    throw parseHttpCode(code)

} catch (e: IOException) {
    throw ConnectionException(e)
}



