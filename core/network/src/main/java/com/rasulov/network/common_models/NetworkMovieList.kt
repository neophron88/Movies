package com.rasulov.network.common_models

import com.squareup.moshi.Json

class NetworkMovieList(
    @Json(name = "results")
    val movies: List<NetworkMovie>
)