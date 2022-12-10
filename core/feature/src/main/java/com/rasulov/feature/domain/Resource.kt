package com.rasulov.feature.domain

import com.rasulov.feature.data.network.BackendSideException
import com.rasulov.feature.data.network.ClientSideException
import com.rasulov.feature.data.network.ConnectionException


sealed class Resource<T>

class Success<T>(
    val value: T
) : Resource<T>()

class Loading<T> : Resource<T>()

class Error<T>(
    val type: ErrorType
) : Resource<T>()

inline fun <T, R> Resource<T>.map(block: (T) -> R): Resource<R> =
    if (this is Success)
        Success(block(this.value))
    else
        this as Resource<R>


sealed class ErrorType
object NoConnection : ErrorType()
object BackendFault : ErrorType()
object Unknown : ErrorType()
object PageEnded : ErrorType()


inline fun <T> wrapNetworkExceptions(block: () -> Unit): Result<T> {
    try {
        block()
    } catch (e: ConnectionException) {

    } catch (e: BackendSideException) {

    } catch (e: ClientSideException) {

    }
    TODO()
}