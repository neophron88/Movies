package com.rasulov.main.data.sources.cache_status.sharedpreferences_impl

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.rasulov.shared.util.CurrentTime
import com.rasulov.main.data.sources.cache_status.*

private const val ALL_GENRES = "all_genres"


class AllGenresCacheInfoSharedPref(
    application: Application,
    private val currentTime: CurrentTime
) : CacheInfo<AllGenresValue, AllGenresCache> {

    private val pref = application.getSharedPreferences(ALL_GENRES, Context.MODE_PRIVATE)

    override suspend fun getCacheStatus(value: AllGenresValue): Status {
        val data = pref.getString(ALL_GENRES, null) ?: return Revalidate()
        val cache = Gson().fromJson(data, AllGenresCache::class.java)
        if (cache.language != value.language) return Revalidate()
        return cache.status(currentTime)
    }


    override suspend fun updateCache(cache: AllGenresCache) {
        val data = Gson().toJson(cache)
        pref.edit().putString(ALL_GENRES, data).apply()
    }

}

class AllGenresCache(
    val language: String,
    maxAge: Int?,
    eTag: String?,
    defaultMaxAgeIfOtherNull: Int,
    updatedTimeInSeconds: Long
) : Cache(maxAge, eTag, defaultMaxAgeIfOtherNull, updatedTimeInSeconds)

class AllGenresValue(
    val language: String
) : Value()




