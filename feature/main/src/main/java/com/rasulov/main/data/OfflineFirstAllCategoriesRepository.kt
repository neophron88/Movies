package com.rasulov.main.data

import com.rasulov.database.all_categories_dao.AllCategoriesDao
import com.rasulov.feature.domain.base.Resource
import com.rasulov.feature.domain.base.queries.BaseQuery
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.main.data.network_datasource.AllCategoriesNetworkDataSource
import com.rasulov.main.data.util.mapper.*
import com.rasulov.main.data.util.offlineFirst
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.queries.GenreChangedQuery
import com.rasulov.main.domain.queries.MoviesByGenreQuery
import com.rasulov.main.domain.repository.AllCategoriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex

class OfflineFirstAllCategoriesRepository(
    private val local: AllCategoriesDao,
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
            syncWithRemote = { network.loadAllGenres(query.toBaseParams()) },
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
            syncWithRemote = {
                network.loadMoviesByGenre(query.toByGenreNetworkParams(alwaysFirstPage))
            },
            updateLocalData = { networkMovies ->
                local.insertMoviesByGenreAfterDeleteOld(
                    networkMovies.toInsertMoviesByGenreTuple(query.genreId)
                )
            }
        )


    override fun loadRecentlyMovies(): Flow<Resource<List<Movie>>> =
        TODO()


}