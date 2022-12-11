package com.rasulov.network.common_models

import com.squareup.moshi.Json

class NetworkMovieList(
    @field:Json(name = "results")
    val results: List<NetworkMovie>,
)