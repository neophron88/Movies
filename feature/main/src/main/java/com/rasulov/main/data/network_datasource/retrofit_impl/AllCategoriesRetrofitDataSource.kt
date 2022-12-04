package com.rasulov.main.data.network_datasource.retrofit_impl

import com.rasulov.common.CurrentTime
import com.rasulov.feature.data.base.networkdatasource.params.BaseNetworkParams
import com.rasulov.main.data.network_datasource.AllCategoriesNetworkDataSource
import com.rasulov.main.data.network_datasource.cache_info.CacheInfo
import com.rasulov.main.data.network_datasource.cache_info.sharedpreferences_impl.AllGenresCache
import com.rasulov.main.data.network_datasource.cache_info.sharedpreferences_impl.AllGenresValue
import com.rasulov.main.data.network_datasource.cache_info.sharedpreferences_impl.GenreCache
import com.rasulov.main.data.network_datasource.cache_info.sharedpreferences_impl.GenreValue
import com.rasulov.main.data.network_datasource.retrofit_impl.params.ByGenreNetworkParams
import com.rasulov.main.data.network_datasource.retrofit_impl.util.*
import com.rasulov.network.all_categories_service.AllCategoriesService
import com.rasulov.network.all_categories_service.models.NetworkGenre
import com.rasulov.network.common_models.NetworkMovie
import com.rasulov.network.common_models.NetworkMovieList

class AllCategoriesRetrofitDataSource(
    private val service: AllCategoriesService,
    private val currentTime: CurrentTime,
    private val genreInterceptor: FamilyGenre,
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