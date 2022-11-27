package com.rasulov.shared.data.remote

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.io.IOException

open class RemoteException(cause: Throwable? = null) : Throwable(cause)

class ConnectionException(cause: Throwable? = null) : RemoteException(cause)

class DataNotOutdatedException(cause: Throwable? = null) : RemoteException(cause)

class BackendException(cause: Throwable? = null) : RemoteException(cause)


suspend fun <T> wrapRetrofitExceptions(
    block: suspend () -> T,
): T {
    try {
        return block()
    } catch (e: HttpException) {
        throw parseHttpException(e)
    } catch (e: IOException) {
        throw ConnectionException(e)
    }

}

private fun parseHttpException(e: HttpException): RemoteException {
    return RemoteException(e)
}