package com.rasulov.main.data.sources.remote.cache_status.sharedpreferences_impl

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.rasulov.feature.util.CurrentTime
import com.rasulov.main.data.sources.remote.cache_status.*

private const val ALL_GENRES = "all_genres"

class AllGenresCache(
    maxAge: Int?,
    eTag: String?,
    defaultMaxAgeIfOtherNull: Int
) : Cache(maxAge, eTag, defaultMaxAgeIfOtherNull)


class AllGenresCacheStatusSharedPref(
    application: Application,
    private val currentTime: CurrentTime
) : CacheStatus<AllGenresCache> {

    private val pref = application.getSharedPreferences(ALL_GENRES, Context.MODE_PRIVATE)

    override suspend fun getCacheStatus(): Status {
        val data = pref.getString(ALL_GENRES, null) ?: return Empty
        val cache = Gson().fromJson(data, AllGenresCache::class.java)
        return cache.status(currentTime)
    }


    override suspend fun updateCacheStatus(cache: AllGenresCache) {
        val data = Gson().toJson(cache)
        pref.edit().putString(ALL_GENRES, data).apply()
    }

}






