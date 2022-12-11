package com.rasulov.feature.data.network

import retrofit2.HttpException
import java.io.IOException

abstract class NetworkException(
    cause: Throwable,
    code: Int? = null,
) : Throwable("Network error code $code", cause)

class ConnectionException(cause: Throwable) : NetworkException(cause)

class BackendSideException(val code: Int, cause: Throwable) : NetworkException(cause, code)

class ClientSideException(val code: Int, cause: Throwable) : NetworkException(cause, code)

class UnknownNetworkException(val code: Int, cause: Throwable) : NetworkException(cause, code)


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

fun parseHttpCodeToException(code: Int, cause: Throwable): NetworkException =
    if (code >= BACKEND) BackendSideException(code, cause)
    else if (code < BACKEND && code >= CLIENT) ClientSideException(code, cause)
    else UnknownNetworkException(code, cause)
