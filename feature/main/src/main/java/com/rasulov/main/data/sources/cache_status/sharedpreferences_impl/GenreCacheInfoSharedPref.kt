package com.rasulov.main.data.sources.cache_status.sharedpreferences_impl

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.rasulov.main.data.sources.cache_status.*
import com.rasulov.shared.util.CurrentTime

private const val GENRE = "genre"

class GenreCacheInfoSharedPref(
    application: Application,
    private val currentTime: CurrentTime
) : CacheInfo<GenreValue, GenreCache> {

    private val pref = application.getSharedPreferences(GENRE, Context.MODE_PRIVATE)

    override suspend fun getCacheStatus(value: GenreValue): Status {
        val data = pref.getString(value.genreId.toString(), null) ?: return Revalidate()
        val cache = Gson().fromJson(data, GenreCache::class.java)
        if (cache.language != value.language) return Revalidate()
        if (cache.sortBy != value.sortBy) return Revalidate()
        return cache.status(currentTime)
    }

    override suspend fun updateCache(cache: GenreCache) {
        val data = Gson().toJson(cache)
        pref.edit().putString(cache.genreId.toString(), data).apply()
    }

}

class GenreCache(
    val language: String,
    val sortBy: String,
    val genreId: Int,
    maxAge: Int?,
    eTag: String?,
    defaultMaxAgeIfOtherNull: Int,
    updatedTimeInSeconds: Long
) : Cache(maxAge, eTag, defaultMaxAgeIfOtherNull, updatedTimeInSeconds)

class GenreValue(
    val language: String,
    val sortBy: String,
    val genreId: Int,
) : Value()

