package com.rasulov.main.data.network_datasource

import com.rasulov.feature.data.base.networkdatasource.params.BaseNetworkParams
import com.rasulov.main.data.network_datasource.retrofit_impl.params.ByGenreNetworkParams
import com.rasulov.network.all_categories_service.models.NetworkGenre
import com.rasulov.network.common_models.NetworkMovie

interface AllCategoriesNetworkDataSource {

    suspend fun loadAllGenres(params: BaseNetworkParams): List<NetworkGenre>

    suspend fun loadMoviesByGenre(params: ByGenreNetworkParams): List<NetworkMovie>


}