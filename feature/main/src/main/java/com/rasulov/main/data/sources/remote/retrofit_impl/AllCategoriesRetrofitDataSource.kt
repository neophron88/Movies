package com.rasulov.main.data.sources.remote.retrofit_impl

import com.rasulov.feature.domain.shared.Movie
import com.rasulov.main.data.sources.remote.AllCategoriesRemoteDataSource
import com.rasulov.main.data.sources.remote.cache_status.CacheStatus
import com.rasulov.main.data.sources.remote.cache_status.sharedpreferences_impl.AllGenresCache
import com.rasulov.main.data.sources.remote.cache_status.sharedpreferences_impl.GenreCache
import com.rasulov.main.data.sources.remote.cache_status.sharedpreferences_impl.TopRatedCache
import com.rasulov.main.domain.entities.Genre

class AllCategoriesRetrofitDataSource(
    val allGenresCacheStatus: CacheStatus<AllGenresCache>,
    val genreCacheStatus: CacheStatus<GenreCache>,
    val topRatedCacheStatus: CacheStatus<TopRatedCache>
) : AllCategoriesRemoteDataSource {

    override suspend fun loadGenresIfCurrentOutdated(): List<Genre> {
        TODO("Not yet implemented")
    }

    override suspend fun loadMoviesByGenreIfCurrentOutdated(genre: Genre): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun loadTopRatedMoviesIfCurrentOutdated(): List<Movie> {
        TODO("Not yet implemented")
    }

}