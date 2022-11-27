package com.rasulov.shared.data.remote.models

import com.squareup.moshi.Json

data class RemoteMovie(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "vote_average")
    val rating: Float
)