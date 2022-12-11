package com.rasulov.main.data.sources.network

import com.rasulov.feature.data.network.params.BaseNetworkParams
import com.rasulov.main.data.sources.network.retrofit_impl.params.ByGenreNetworkParams
import com.rasulov.network.all_genres_service.models.NetworkGenre
import com.rasulov.network.common_models.NetworkMovie

interface AllCategoriesNetworkDataSource {

    suspend fun loadAllGenres(params: BaseNetworkParams): List<NetworkGenre>

    suspend fun loadMoviesByGenre(params: ByGenreNetworkParams): List<NetworkMovie>

    suspend fun loadTopRated(params: BaseNetworkParams): List<NetworkMovie>


}