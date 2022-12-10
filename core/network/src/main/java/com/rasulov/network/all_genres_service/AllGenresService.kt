package com.rasulov.network.all_genres_service

import com.rasulov.network.all_genres_service.models.NetworkGenre
import com.rasulov.network.common_models.NetworkMovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface AllGenresService {

    @GET("genre/movie/list")
    suspend fun loadAllGenres(
        @Header("If-None-Match") eTag: String?,
        @QueryMap params: Map<String, String>
    ): Response<List<NetworkGenre>>


    @GET("discover/movie")
    suspend fun loadMoviesByGenre(
        @Header("If-None-Match") eTag: String?,
        @QueryMap params: Map<String, Any?>,
    ): Response<NetworkMovieList>


}