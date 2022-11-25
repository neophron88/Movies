package com.rasulov.main.data.sources.remote.cache_status

import com.rasulov.feature.util.CurrentTime

interface CacheStatus<C : Cache> {

    suspend fun getCacheStatus(): Status

    suspend fun updateCacheStatus(cache: C)

}


sealed class Status
object NotOutdated : Status()
object Empty : Status()
class Revalidate(eTag: String) : Status()


open class Cache(
    val maxAge: Int?,
    val eTag: String?,
    val defaultMaxAgeIfOtherNull: Int
)

