package com.rasulov.feature.data.network.family_genre

import android.app.Application
import android.content.Context
import android.util.Log
import com.rasulov.network.all_genres_service.models.NetworkGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val NOT_FAMILY = "not_family"
private const val FAMILY = "family"

class FamilyGenreInterceptor(
    application: Application
) {
    private val dispatcherIO = Dispatchers.IO
    private val pref = application.getSharedPreferences("interceptor", Context.MODE_PRIVATE)

    private val notFamilyKeywords = listOf(
        "ужас", "horror",
        "музык", "music",
        "мелодрам", "romanc",
    )

    private val familyKeyword = listOf("famil", "семейн")


    suspend fun defineAndSaveNotFamilyGenres(genres: List<NetworkGenre>) =
        withContext(dispatcherIO) {
            val genresIds = mutableSetOf<String>()
            for (genre in genres) {
                for (keyword in notFamilyKeywords) {
                    val contains = genre.name.contains(keyword, true)
                    if (contains) {
                        genresIds.add(genre.id.toString())
                        break
                    }
                }
            }
            pref.edit().putStringSet(NOT_FAMILY, genresIds).commit()
            Unit
        }

    suspend fun defineAndSaveFamilyGenre(genres: List<NetworkGenre>) =
        withContext(dispatcherIO) {
            var id: Int? = null
            for (keyword in familyKeyword) {
                val genre = genres.find {
                    it.name.contains(keyword, true) }

                id = genre?.id
                if(id != null) break
            }

            if (id == null) error("List from network must contain family genre")
            pref.edit().putInt(FAMILY, id).commit()
            Unit
        }

    suspend fun filterFamilyGenres(genres: List<NetworkGenre>): List<NetworkGenre> =
        withContext(dispatcherIO) {
            val set = pref.getStringSet(NOT_FAMILY, null) ?: setOf()
            genres.filterNot { set.contains(it.id.toString()) }
        }

    suspend fun getFamilyGenreId(): Int =
        withContext(dispatcherIO) {
            val genre = pref.getInt(FAMILY, -1)
            if (genre == -1) throw IllegalStateException("Preference must contain  family genre")
            genre
        }

    suspend fun getNotFamilyGenreIds(): List<Int> =
        withContext(dispatcherIO) {
            val set = pref.getStringSet(NOT_FAMILY, null) ?: setOf()
            set.map { it.toInt() }
        }

}
