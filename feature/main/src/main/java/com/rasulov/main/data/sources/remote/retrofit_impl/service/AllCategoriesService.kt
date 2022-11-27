package com.rasulov.main.data.sources.remote.retrofit_impl.service

import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.RemoteGenre
import com.rasulov.shared.domain.models.Movie
import com.rasulov.main.domain.entities.Genre
import com.rasulov.shared.data.remote.models.RemoteMovie
import retrofit2.Response
import retrofit2.http.GET

interface AllCategoriesService {

    @GET
    suspend fun loadAllGenres(eTag: String?): Response<List<RemoteGenre>>

    @GET
    suspend fun loadMoviesByGenre(): Response<List<RemoteMovie>>

    @GET
    suspend fun loadTopRatedMovies(): Response<List<RemoteMovie>>


}