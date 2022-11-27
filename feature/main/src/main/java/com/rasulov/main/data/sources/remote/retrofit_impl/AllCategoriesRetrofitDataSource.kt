package com.rasulov.main.data.sources.remote.retrofit_impl

import com.rasulov.main.data.sources.cache_status.CacheInfo
import com.rasulov.main.data.sources.cache_status.NotOutdated
import com.rasulov.main.data.sources.cache_status.Revalidate
import com.rasulov.main.data.sources.cache_status.sharedpreferences_impl.*
import com.rasulov.main.data.sources.remote.AllCategoriesRemoteDataSource
import com.rasulov.main.data.sources.remote.retrofit_impl.service.*
import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.BaseParams
import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.ByGenreParams
import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.RemoteGenre
import com.rasulov.main.data.sources.remote.retrofit_impl.util.load
import com.rasulov.main.data.sources.remote.retrofit_impl.util.toAllGenresCache
import com.rasulov.shared.data.remote.models.RemoteMovie
import com.rasulov.shared.util.CurrentTime

class AllCategoriesRetrofitDataSource(
    private val service: AllCategoriesService,
    private val currentTime: CurrentTime,
    private val allGenresCacheInfo: CacheInfo<AllGenresValue, AllGenresCache>,
    private val genreCacheInfo: CacheInfo<GenreValue, GenreCache>,
    private val topRatedCacheInfo: CacheInfo<TopRatedValue, TopRatedCache>
) : AllCategoriesRemoteDataSource {


    override suspend fun loadAllGenres(params: BaseParams): List<RemoteGenre> {
        val status = allGenresCacheInfo.getCacheStatus(AllGenresValue(params.language))
        return when (status) {
            is Revalidate -> load(
                request = { service.loadAllGenres(status.eTag) },
                updateCacheInfo = {
                    allGenresCacheInfo.updateCache(it.toAllGenresCache(params, currentTime))
                }
            )

            is NotOutdated -> emptyList()
        }

    }

    override suspend fun loadMoviesByGenre(params: ByGenreParams): List<RemoteMovie> {
        TODO()
    }

    override suspend fun loadTopRatedMovies(params: BaseParams): List<RemoteMovie> {
        TODO()
    }

}