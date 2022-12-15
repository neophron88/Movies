package com.rasulov.network.all_genres_service

import com.rasulov.network.all_genres_service.models.AllNetworkGenres
import com.rasulov.network.common_models.NetworkMovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface AllGenresService {

    @GET("genre/movie/list")
    suspend fun loadAllGenres(
        @Header("If-None-Match") eTag: String?,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): Response<AllNetworkGenres>


    @GET("discover/movie")
    suspend fun loadMoviesByGenre(
        @Header("If-None-Match") eTag: String?,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
    ): Response<NetworkMovieList>


    @GET("movie/popular")
    suspend fun loadTopRated(
        @Header("If-None-Match") eTag: String?,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): Response<NetworkMovieList>

}