package com.rasulov.main.data.sources.cache_status.sharedpreferences_impl

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.rasulov.main.data.sources.cache_status.*
import com.rasulov.shared.util.CurrentTime

private const val TOP_RATED = "top_rated"

class TopRatedCacheInfoSharedPref(
    application: Application,
    private val currentTime: CurrentTime
) : CacheInfo<TopRatedValue, TopRatedCache> {

    private val pref = application.getSharedPreferences(TOP_RATED, Context.MODE_PRIVATE)

    override suspend fun getCacheStatus(value: TopRatedValue): Status {
        val data = pref.getString(TOP_RATED, null) ?: return Revalidate()
        val cache = Gson().fromJson(data, TopRatedCache::class.java)
        if (cache.language != value.language) return Revalidate()
        return cache.status(currentTime)
    }

    override suspend fun updateCache(cache: TopRatedCache) {
        val data = Gson().toJson(cache)
        pref.edit().putString(TOP_RATED, data).apply()
    }
}


class TopRatedCache(
    val language: String,
    maxAge: Int?,
    eTag: String?,
    defaultMaxAgeIfOtherNull: Int,
    updatedTimeInSeconds: Long
) : Cache(maxAge, eTag, defaultMaxAgeIfOtherNull, updatedTimeInSeconds)

class TopRatedValue(
    val language: String
) : Value()

