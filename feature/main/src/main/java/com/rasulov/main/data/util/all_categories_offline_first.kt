package com.rasulov.main.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first


inline fun <I> offlineFirst(
    crossinline localDataFlow: () -> Flow<List<I>>,
    crossinline syncWithRemote: suspend () -> List<I>,
    crossinline updateLocalData: suspend (List<I>) -> Unit
): Flow<List<I>> = channelFlow {

    val localData = localDataFlow()
    send(localData.first())

    val remoteDataList = syncWithRemote()
    if (remoteDataList.isNotEmpty())
        updateLocalData(remoteDataList)

    localData.collect { send(it) }

}