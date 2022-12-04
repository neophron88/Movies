package com.rasulov.database.common_models

class MovieDetailEntity(
    val id: Int,
    val genreId: Int?,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val rating: Float
)