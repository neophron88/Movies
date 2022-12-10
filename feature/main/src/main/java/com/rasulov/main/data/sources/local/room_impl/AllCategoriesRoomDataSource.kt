package com.rasulov.main.data.sources.local.room_impl

import com.rasulov.database.all_genres_dao.AllGenresDao
import com.rasulov.database.all_genres_dao.models.GenreSettingsEntity
import com.rasulov.database.all_genres_dao.tuples.GenreWithSettingsTuple
import com.rasulov.database.all_genres_dao.tuples.InsertGenresTuple
import com.rasulov.database.all_genres_dao.tuples.InsertMoviesByGenreTuple
import com.rasulov.database.common_models.MovieEntity
import com.rasulov.main.data.sources.local.AllCategoriesLocalDataSource
import kotlinx.coroutines.flow.Flow

class AllCategoriesRoomDataSource(
    private val allGenresDao: AllGenresDao
) : AllCategoriesLocalDataSource {

    override fun getGenresFlow(): Flow<List<GenreWithSettingsTuple>> =
        allGenresDao.getGenresFlow()

    override suspend fun insertGenresAfterDeleteOld(tuple: InsertGenresTuple) =
        allGenresDao.insertGenresAfterDeleteOld(tuple)


    override suspend fun setGenreSettings(entity: GenreSettingsEntity) =
        allGenresDao.setGenreSettings(entity)


    override fun getMoviesByGenreFlow(genreId: Int): Flow<List<MovieEntity>> =
        allGenresDao.getMoviesByGenreFlow(genreId)


    override suspend fun insertMoviesByGenreAfterDeleteOld(tuple: InsertMoviesByGenreTuple) =
        allGenresDao.insertMoviesByGenreAfterDeleteOld(tuple)


    override fun loadRecentlyMovies(): Flow<List<MovieEntity>> =
        TODO()

}

