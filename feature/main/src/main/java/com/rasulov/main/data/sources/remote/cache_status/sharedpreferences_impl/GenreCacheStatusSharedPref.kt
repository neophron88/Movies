package com.rasulov.main.data.sources.remote.cache_status.sharedpreferences_impl

import android.app.Application
import android.content.Context
import com.rasulov.main.data.sources.remote.cache_status.Cache
import com.rasulov.main.data.sources.remote.cache_status.CacheStatus
import com.rasulov.main.data.sources.remote.cache_status.Status

private const val GENRE = "genre"

class GenreCache(
    val genreId: Int,
    maxAge: Int?,
    eTag: String?,
    defaultMaxAgeIfOtherNull: Int
) : Cache(maxAge, eTag, defaultMaxAgeIfOtherNull)

class GenreCacheStatusSharedPref(
    application: Application
) : CacheStatus<GenreCache> {

    private val genre = application.getSharedPreferences(GENRE, Context.MODE_PRIVATE)

    override suspend fun getCacheStatus(): Status {
        TODO("Not yet implemented")
    }

    override suspend fun updateCacheStatus(cache: GenreCache) {
        TODO("Not yet implemented")
    }

}

