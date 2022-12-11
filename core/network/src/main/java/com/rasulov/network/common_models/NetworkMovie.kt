package com.rasulov.network.common_models

import com.squareup.moshi.Json

class NetworkMovie(
    val id: Int,
    val title: String,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "backdrop_path")
    val backdropPath: String?,
    @field:Json(name = "release_date")
    val releaseDate: String,
    @field:Json(name = "vote_average")
    val rating: Float
)