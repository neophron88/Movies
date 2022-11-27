package com.rasulov.main.data.sources.remote

import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.BaseParams
import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.ByGenreParams
import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.RemoteGenre
import com.rasulov.shared.data.remote.models.RemoteMovie

interface AllCategoriesRemoteDataSource {

    suspend fun loadAllGenres(params: BaseParams): List<RemoteGenre>

    suspend fun loadMoviesByGenre(params: ByGenreParams): List<RemoteMovie>

    suspend fun loadTopRatedMovies(params: BaseParams): List<RemoteMovie>

}