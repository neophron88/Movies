package com.rasulov.main.data.network_datasource.cache_info

interface CacheInfo<V : Value, C : Cache> {

    suspend fun getCacheStatus(value: V): Status

    suspend fun updateCache(cache: C)


    companion object {
        const val DEFAULT_MAX_AGE = 24 * 60 * 60 //day * hour * minute = in Seconds
    }

}


sealed class Status
object NotOutdated : Status()
class Revalidate(val eTag: String? = null) : Status()


open class Value

open class Cache(
    val maxAge: Int?,
    val eTag: String?,
    val defaultMaxAgeIfOtherNull: Int,
    val updatedTimeInSeconds: Long
)

