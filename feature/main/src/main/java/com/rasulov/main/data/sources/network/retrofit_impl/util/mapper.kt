package com.rasulov.main.data.sources.network.retrofit_impl.util

import com.rasulov.feature.data.network.params.BaseNetworkParams
import com.rasulov.feature.data.network.CacheHeader
import com.rasulov.feature.data.network.cache_info.CacheInfo
import com.rasulov.feature.data.network.family_genre.FamilyGenreInterceptor
import com.rasulov.feature.utils.CurrentTime
import com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl.AllGenresCache
import com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl.GenreCache
import com.rasulov.main.data.sources.network.retrofit_impl.params.ByGenreNetworkParams


fun CacheHeader.toAllGenresCache(params: BaseNetworkParams, currentTime: CurrentTime) =
    AllGenresCache(
        language = params.language,
        maxAge = maxAge,
        eTag = eTag,
        defaultMaxAgeIfOtherNull = CacheInfo.DEFAULT_MAX_AGE,
        updatedTimeInSeconds = currentTime.getTimeInSeconds()
    )

fun CacheHeader.toGenreCache(params: ByGenreNetworkParams, currentTime: CurrentTime) =
    GenreCache(
        language = params.language,
        sortBy = params.sortBy,
        genreId = params.genreId,
        maxAge = maxAge,
        eTag = eTag,
        defaultMaxAgeIfOtherNull = CacheInfo.DEFAULT_MAX_AGE,
        updatedTimeInSeconds = currentTime.getTimeInSeconds()
    )


fun BaseNetworkParams.toQueryMap() = mapOf(
    BaseNetworkParams.LANGUAGE_KEY to language
)

suspend fun ByGenreNetworkParams.toQueryMap(genre: FamilyGenreInterceptor) = mapOf(
    BaseNetworkParams.LANGUAGE_KEY to this.language,
    BaseNetworkParams.PAGE_KEY to this.page,
    ByGenreNetworkParams.SORT_BY_KEY to this.sortBy,
    ByGenreNetworkParams.WITH_GENRES_KEY to listOf(this.genreId, genre.getFamilyGenreId()),
    ByGenreNetworkParams.WITHOUT_GENRES_KEY to genre.getNotFamilyGenreIds()
)


