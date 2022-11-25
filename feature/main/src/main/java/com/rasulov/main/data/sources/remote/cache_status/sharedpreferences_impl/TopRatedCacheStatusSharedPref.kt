package com.rasulov.main.data.sources.remote.cache_status.sharedpreferences_impl

import android.app.Application
import android.content.Context
import com.rasulov.main.data.sources.remote.cache_status.Cache
import com.rasulov.main.data.sources.remote.cache_status.CacheStatus
import com.rasulov.main.data.sources.remote.cache_status.Status

private const val TOP_RATED = "top_rated"

class TopRatedCache(
    maxAge: Int?,
    eTag: String?,
    defaultMaxAgeIfOtherNull: Int
) : Cache(maxAge, eTag, defaultMaxAgeIfOtherNull)

class TopRatedCacheStatusSharedPref(
    application: Application
) : CacheStatus<TopRatedCache> {

    private val topRated = application.getSharedPreferences(TOP_RATED, Context.MODE_PRIVATE)

    override suspend fun getCacheStatus(): Status {
        TODO("Not yet implemented")
    }

    override suspend fun updateCacheStatus(update: TopRatedCache) {
        TODO("Not yet implemented")
    }

}

