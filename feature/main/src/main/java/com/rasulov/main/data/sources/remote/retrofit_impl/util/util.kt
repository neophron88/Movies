package com.rasulov.main.data.sources.remote.retrofit_impl.util

import com.rasulov.shared.data.remote.BackendException
import com.rasulov.shared.data.remote.ConnectionException
import com.rasulov.shared.data.remote.RemoteException
import retrofit2.Response
import java.io.IOException


const val NOT_MODIFIED = 302
const val BACKEND = 500


inline fun <T> load(
    request: () -> Response<List<T>>,
    updateCacheInfo: (CacheHeader) -> Unit
): List<T> = try {
    val response = request()
    val code = response.code()
    if (response.isSuccessful || code == NOT_MODIFIED) {
        val cacheHeader = response.parseCacheRelatedHeaders()
        updateCacheInfo(cacheHeader)
        response.body() ?: emptyList()
    }

    throw parseUnsuccessfulResponse(code)

} catch (e: IOException) {
    throw ConnectionException(e)
}

fun parseUnsuccessfulResponse(code: Int): RemoteException =
    if (code >= BACKEND) BackendException()
    else RemoteException()
    //TODO

