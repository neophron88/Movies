package com.rasulov.main.data.sources.local

import com.rasulov.database.all_genres_dao.models.GenreSettingsEntity
import com.rasulov.database.all_genres_dao.tuples.GenreWithSettingsTuple
import com.rasulov.database.all_genres_dao.tuples.InsertGenresTuple
import com.rasulov.database.all_genres_dao.tuples.InsertMoviesByGenreTuple
import com.rasulov.database.common_models.MovieEntity
import kotlinx.coroutines.flow.Flow

interface AllCategoriesLocalDataSource {

    fun getGenresFlow(): Flow<List<GenreWithSettingsTuple>>

    suspend fun insertGenresAfterDeleteOld(tuple: InsertGenresTuple)

    suspend fun setGenreSettings(entity: GenreSettingsEntity)


    fun getMoviesByGenreFlow(genreId: Int): Flow<List<MovieEntity>>

    suspend fun insertMoviesByGenreAfterDeleteOld(tuple: InsertMoviesByGenreTuple)


    fun loadRecentlyMovies(): Flow<List<MovieEntity>>


}