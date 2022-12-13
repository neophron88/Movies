package com.rasulov.main.data.util

import android.util.Log
import com.rasulov.feature.data.network.BackendSideException
import com.rasulov.feature.data.network.ClientSideException
import com.rasulov.feature.data.network.ConnectionException
import com.rasulov.feature.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed class SyncResult
object SyncSuccess : SyncResult()
class SyncFault(val type: ErrorType) : SyncResult()


inline fun <I, R> offlineFirst(
    str: String,
    mutex: Mutex,
    longLiveScope: CoroutineScope,
    crossinline localDataFlow: () -> Flow<List<I>>,
    crossinline syncWithNetwork: suspend () -> List<R>,
    crossinline updateLocalData: suspend (List<R>) -> Unit
): Flow<Resource<List<I>>> = flow {

    val localData = localDataFlow()
    emit(Loading())

    val syncResult = longLiveScope.async {
        mutex.withLock {
            try {
                val networkDataList = syncWithNetwork()
                if (networkDataList.isNotEmpty())
                    updateLocalData(networkDataList)
                SyncSuccess
            } catch (e: ConnectionException) {
                SyncFault(NoConnection)
            } catch (e: BackendSideException) {
                SyncFault(BackendFault)
            } catch (e: ClientSideException) {
                SyncFault(Unknown)
            }
        }

    }.await()

    if (syncResult is SyncFault) emit(Error(syncResult.type))

    localData.collect { emit(Success(it)) }
}



