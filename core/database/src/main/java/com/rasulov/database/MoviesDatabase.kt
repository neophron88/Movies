package com.rasulov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rasulov.database.all_genres_dao.AllGenresDao
import com.rasulov.database.all_genres_dao.models.GenreEntity
import com.rasulov.database.all_genres_dao.models.GenreSettingsEntity
import com.rasulov.database.common_models.MovieEntity

@Database(
    version = 1,
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        GenreSettingsEntity::class
    ],
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getAllCategoriesDao(): AllGenresDao


    companion object {
        @Volatile
        private var database: MoviesDatabase? = null

        fun getDatabase(appContext: Context): MoviesDatabase {
            val temp1 = database
            if (temp1 != null) return temp1

            synchronized(this) {
                val temp2 = database
                if (temp2 != null) return temp2
                val temp3 = Room.databaseBuilder(
                    appContext,
                    MoviesDatabase::class.java,
                    "movies_db"
                ).build()
                database = temp3
                return temp3
            }
        }
    }

}