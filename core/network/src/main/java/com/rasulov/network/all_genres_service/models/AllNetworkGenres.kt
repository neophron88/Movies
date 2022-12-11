package com.rasulov.network.all_genres_service.models

import com.squareup.moshi.Json

class AllNetworkGenres(
    @field:Json(name = "genres")
    val genres: List<NetworkGenre>
)
