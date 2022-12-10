package com.rasulov.main.data.util

import com.rasulov.feature.data.network.BackendSideException
import com.rasulov.feature.data.network.ClientSideException
import com.rasulov.feature.data.network.ConnectionException
import com.rasulov.feature.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed class SyncResult
object SyncSuccess : SyncResult()
class SyncFault(val type: ErrorType) : SyncResult()


inline fun <I, R> offlineFirst(
    mutex: Mutex,
    longLiveScope: CoroutineScope,
    crossinline localDataFlow: () -> Flow<List<I>>,
    crossinline syncWithRemote: suspend () -> List<R>,
    crossinline updateLocalData: suspend (List<R>) -> Unit
): Flow<Resource<List<I>>> = channelFlow {

    val localData = localDataFlow()
    send(Success(localData.first()))

    val syncResult = longLiveScope.async {
        mutex.withLock {
            try {
                val remoteDataList = syncWithRemote()
                if (remoteDataList.isNotEmpty())
                    updateLocalData(remoteDataList)
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

    if (syncResult is SyncFault) send(Error(syncResult.type))

    localData.collect { send(Success(it)) }
}



