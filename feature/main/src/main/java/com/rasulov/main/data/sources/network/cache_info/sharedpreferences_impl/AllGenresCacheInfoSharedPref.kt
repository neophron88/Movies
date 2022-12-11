package com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.rasulov.feature.data.network.cache_info.*
import com.rasulov.feature.utils.CurrentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val ALL_GENRES = "all_genres"


class AllGenresCacheInfoSharedPref(
    application: Application,
    private val currentTime: CurrentTime
) : CacheInfo<AllGenresValue, AllGenresCache> {

    private val pref = application.getSharedPreferences(ALL_GENRES, Context.MODE_PRIVATE)
    private val dispatcherIO = Dispatchers.IO

    override suspend fun getCacheStatus(value: AllGenresValue): Status =
        withContext(dispatcherIO) {
            val data = pref.getString(ALL_GENRES, null) ?: return@withContext Revalidate()
            val cache = Gson().fromJson(data, AllGenresCache::class.java)
            if (cache.language != value.language) return@withContext Revalidate()
            cache.status(currentTime)
        }


    override suspend fun updateCache(cache: AllGenresCache) =
        withContext(dispatcherIO) {
            val data = Gson().toJson(cache)
            pref.edit().putString(ALL_GENRES, data).commit()
            Unit
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




