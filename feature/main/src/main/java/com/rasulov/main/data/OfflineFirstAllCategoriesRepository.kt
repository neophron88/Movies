package com.rasulov.main.data

import com.rasulov.shared.domain.models.Movie
import com.rasulov.main.data.sources.local.AllCategoriesLocalDataSource
import com.rasulov.main.data.sources.remote.AllCategoriesRemoteDataSource
import com.rasulov.main.data.util.offlineFirst
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.queries.GenreChanged
import com.rasulov.main.domain.repository.AllCategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex

class OfflineFirstAllCategoriesRepository(
    private val local: AllCategoriesLocalDataSource,
    private val remote: AllCategoriesRemoteDataSource,
) : AllCategoriesRepository {

    private val mutex = Mutex()


    override fun getGenresFlow(): Flow<List<Genre>> =
        offlineFirst(
            localDataFlow = { local.getGenresFlow() },
            syncWithRemote = { remote.loadAllGenres() },
            updateLocalData = { local.updateGenres(it) }
        )


    override fun changeGenreSortBy(genre: GenreChanged) {
        local.changeGenre(genre)
    }

    override suspend fun loadMoviesByGenre(genre: Genre): Flow<List<Movie>> =
        offlineFirst(
            localDataFlow = { local.getMoviesByGenreFlow(genre) },
            syncWithRemote = { remote.loadMoviesByGenre(genre) },
            updateLocalData = { local.updateMoviesByGenre(it, genre) }
        )


    override suspend fun loadTopRatedMovies(): Flow<List<Movie>> =
        offlineFirst(
            localDataFlow = { local.getTopRatedMoviesFlow() },
            syncWithRemote = { remote.loadTopRatedMovies() },
            updateLocalData = { local.updateTopRatedMovies(it) }
        )


    override suspend fun loadRecentlyMovies(): Flow<List<Movie>> =
        local.getRecentlyMoviesFlow()


}