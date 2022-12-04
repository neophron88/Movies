package com.rasulov.main.data.network_datasource.retrofit_impl.util

import android.app.Application
import android.content.Context
import com.rasulov.network.all_categories_service.models.NetworkGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val KEYWORDS = "keywords"
private const val FAMILY = "family"

class FamilyGenre(
    application: Application
) {
    private val dispatcherIO = Dispatchers.IO
    private val pref = application.getSharedPreferences("interceptor", Context.MODE_PRIVATE)

    private val keywords = listOf(
        "ужас", "horror",
        "музык", "music",
        "мелодрам", "Romanc",
    )

    private val familyKeyword = listOf("famil", "семейн")


    suspend fun defineAndSaveNotFamilyGenres(genres: List<NetworkGenre>) =
        withContext(dispatcherIO) {
            val genresIds = mutableSetOf<String>()
            for (genre in genres) {
                for (keyword in keywords) {
                    val contains = genre.name.contains(keyword, true)
                    if (contains) {
                        genresIds.add(genre.id.toString())
                        break
                    }
                }
            }
            pref.edit().putStringSet(KEYWORDS, genresIds).commit()
            Unit
        }

    suspend fun filterNotFamilyGenres(genres: List<NetworkGenre>): List<NetworkGenre> =
        withContext(dispatcherIO) {
            val set = pref.getStringSet(KEYWORDS, null) ?: setOf()
            genres.filter { set.contains(it.id.toString()) }
        }

    suspend fun defineAndSaveFamilyGenre(genres: List<NetworkGenre>) =
        withContext(dispatcherIO) {
            var id: Int? = null
            for (keyword in familyKeyword) {
                val genre = genres.find { it.name.contains(keyword, true) }
                id = genre?.id
            }

            if (id == null) throw IllegalStateException("List must contain family genre")
            pref.edit().putInt(FAMILY, id).commit()
            Unit
        }

    suspend fun getFamilyGenreId(): Int =
        withContext(dispatcherIO) {
            val genre = pref.getInt(FAMILY, -1)
            if (genre == -1) throw IllegalStateException("Preference must contain  family genre")
            genre
        }

    suspend fun getNotFamilyGenreIds(): List<Int> =
        withContext(dispatcherIO) {
            val set = pref.getStringSet(KEYWORDS, null) ?: setOf()
            set.map { it.toInt() }
        }

}
