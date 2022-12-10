package com.rasulov.database.all_genres_dao

import androidx.room.*
import com.rasulov.database.all_genres_dao.models.GenreEntity
import com.rasulov.database.all_genres_dao.models.GenreSettingsEntity
import com.rasulov.database.all_genres_dao.tuples.*
import com.rasulov.database.common_models.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AllGenresDao {

    @Transaction
    @Query("SELECT * FROM genre_table")
    abstract fun getGenresFlow(): Flow<List<GenreWithSettingsTuple>>

    @Transaction
    open suspend fun insertGenresAfterDeleteOld(tuple: InsertGenresTuple) {
        deleteAllGenres()
        insertGenres(tuple.genres)
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun setGenreSettings(entity: GenreSettingsEntity)


    @Query("SELECT * FROM movie_table WHERE genre_id = :genreId")
    abstract fun getMoviesByGenreFlow(genreId: Int): Flow<List<MovieEntity>>

    @Transaction
    open suspend fun insertMoviesByGenreAfterDeleteOld(tuple: InsertMoviesByGenreTuple) {
        deleteMoviesByGenre(tuple.genreId)
        insertMovies(tuple.movies)
    }





    @Insert
    internal abstract suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genre_table")
    internal abstract suspend fun deleteAllGenres()


    @Insert
    internal abstract suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movie_table WHERE genre_id = :genreId")
    internal abstract suspend fun deleteMoviesByGenre(genreId: Int)

}