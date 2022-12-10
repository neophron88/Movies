package com.rasulov.main.data.sources.network.retrofit_impl

import com.rasulov.feature.data.network.params.BaseNetworkParams
import com.rasulov.feature.data.network.cache_info.CacheInfo
import com.rasulov.feature.data.network.family_genre.FamilyGenreInterceptor
import com.rasulov.feature.utils.CurrentTime
import com.rasulov.main.data.sources.network.AllCategoriesNetworkDataSource
import com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl.AllGenresCache
import com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl.AllGenresValue
import com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl.GenreCache
import com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl.GenreValue
import com.rasulov.main.data.sources.network.retrofit_impl.params.ByGenreNetworkParams
import com.rasulov.main.data.sources.network.retrofit_impl.util.*
import com.rasulov.network.all_genres_service.AllGenresService
import com.rasulov.network.all_genres_service.models.NetworkGenre
import com.rasulov.network.common_models.NetworkMovie
import com.rasulov.network.common_models.NetworkMovieList

class AllCategoriesRetrofitDataSource(
    private val service: AllGenresService,
    private val currentTime: CurrentTime,
    private val genreInterceptor: FamilyGenreInterceptor,
    private val allGenresCacheInfo: CacheInfo<AllGenresValue, AllGenresCache>,
    private val genreCacheInfo: CacheInfo<GenreValue, GenreCache>,
) : AllCategoriesNetworkDataSource {

    override suspend fun loadAllGenres(params: BaseNetworkParams): List<NetworkGenre> {
        val list: List<NetworkGenre> = checkStatusAndLoadIfOutdated(
            getStatus = { allGenresCacheInfo.getCacheStatus(AllGenresValue(params.language)) },
            request = { service.loadAllGenres(it.eTag, params.toQueryMap()) },
            updateCacheInfo = {
                allGenresCacheInfo.updateCache(it.toAllGenresCache(params, currentTime))
            },
            ifNotOutDated = { emptyList() }
        )
        return if (list.isNotEmpty()) {
            genreInterceptor.defineAndSaveFamilyGenre(list)
            genreInterceptor.defineAndSaveNotFamilyGenres(list)
            genreInterceptor.filterNotFamilyGenres(list)
        } else list
    }


    override suspend fun loadMoviesByGenre(params: ByGenreNetworkParams): List<NetworkMovie> =
        checkStatusAndLoadIfOutdated(
            getStatus = {
                genreCacheInfo.getCacheStatus(
                    GenreValue(params.language, params.sortBy, params.genreId)
                )
            },
            request = { service.loadMoviesByGenre(it.eTag, params.toQueryMap(genreInterceptor)) },
            updateCacheInfo = {
                genreCacheInfo.updateCache(it.toGenreCache(params, currentTime))
            },
            ifNotOutDated = { NetworkMovieList(emptyList()) }
        ).movies


}