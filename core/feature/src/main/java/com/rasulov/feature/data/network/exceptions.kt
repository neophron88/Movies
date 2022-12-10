package com.rasulov.feature.data.network

import retrofit2.HttpException
import java.io.IOException

open class NetworkException(cause: Throwable? = null) : Throwable(cause)

class ConnectionException(cause: Throwable? = null) : NetworkException(cause)

class BackendSideException(cause: Throwable? = null) : NetworkException(cause)

class ClientSideException(val code: Int, cause: Throwable? = null) : NetworkException(cause)

class UnknownNetworkException(cause: Throwable? = null) : NetworkException(cause)


inline fun <T> wrapRetrofitExceptions(
    block: () -> T,
): T {
    try {
        return block()
    } catch (e: HttpException) {
        throw parseHttpCodeToException(e.code(), e)
    } catch (e: IOException) {
        throw ConnectionException(e)
    }

}

fun parseHttpCodeToException(code: Int, cause: Throwable? = null): NetworkException =
    if (code >= BACKEND) BackendSideException(cause)
    else if (code < BACKEND && code >= CLIENT) ClientSideException(code, cause)
    else UnknownNetworkException(cause)
