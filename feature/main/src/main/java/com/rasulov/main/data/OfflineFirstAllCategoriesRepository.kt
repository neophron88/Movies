package com.rasulov.main.data

import com.rasulov.feature.data.network.params.BaseNetworkParams
import com.rasulov.feature.domain.BaseQuery
import com.rasulov.feature.domain.Movie
import com.rasulov.feature.domain.Resource
import com.rasulov.feature.domain.Success
import com.rasulov.main.data.sources.local.AllCategoriesLocalDataSource
import com.rasulov.main.data.sources.network.AllCategoriesNetworkDataSource
import com.rasulov.main.data.util.mapper.*
import com.rasulov.main.data.util.offlineFirst
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.queries.GenreChangedQuery
import com.rasulov.main.domain.queries.MoviesByGenreQuery
import com.rasulov.main.domain.repository.AllCategoriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex

class OfflineFirstAllCategoriesRepository(
    private val local: AllCategoriesLocalDataSource,
    private val network: AllCategoriesNetworkDataSource,
    private val longLiveScope: CoroutineScope
) : AllCategoriesRepository {

    private val mutex = Mutex()

    companion object {
        private const val alwaysFirstPage = 1
    }

    override fun getGenresFlow(query: BaseQuery): Flow<Resource<List<Genre>>> =
        offlineFirst(
            mutex = mutex,
            longLiveScope = longLiveScope,
            localDataFlow = { local.getGenresFlow().map { it.toGenres() } },
            syncWithNetwork = { network.loadAllGenres(query.toBaseParams()) },
            updateLocalData = { list ->
                local.insertGenresAfterDeleteOld(list.toInsertGenresTuple())
            }
        )


    override suspend fun setGenreSettings(query: GenreChangedQuery) {
        local.setGenreSettings(query.toGenreSettingsEntity())
    }


    override fun loadMoviesByGenre(query: MoviesByGenreQuery): Flow<Resource<List<Movie>>> =
        offlineFirst(
            mutex = mutex,
            longLiveScope = longLiveScope,
            localDataFlow = {
                local.getMoviesByGenreFlow(query.genreId).map { it.toMovies() }
            },
            syncWithNetwork = {
                network.loadMoviesByGenre(query.toByGenreNetworkParams(alwaysFirstPage))
            },
            updateLocalData = { networkMovies ->
                local.insertMoviesByGenreAfterDeleteOld(
                    networkMovies.toInsertMoviesByGenreTuple(query.genreId)
                )
            }
        )


    override fun loadRecentlyMovies(): Flow<Resource<List<Movie>>> =
        flow {
            val value = network.loadTopRated(
                BaseNetworkParams(language = BaseNetworkParams.EN)
            ).map {
                Movie(
                    it.id,
                    it.posterPath,
                    it.backdropPath,
                    it.title,
                    it.releaseDate,
                    it.rating.toString()
                )
            }
            emit(Success(value))
        }


}