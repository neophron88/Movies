package com.rasulov.feature.data.base.networkdatasource

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
        throw parseHttpCode(e.code())
    } catch (e: IOException) {
        throw ConnectionException(e)
    }

}

fun parseHttpCode(code: Int): NetworkException =
    if (code >= BACKEND) BackendSideException()
    else if (code < BACKEND && code >= CLIENT) ClientSideException(code)
    else UnknownNetworkException()
