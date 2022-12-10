package com.rasulov.feature.domain

data class Movie(
    val id: Int,
    val posterPath: String,
    val backdropPath: String,
    val title: String,
    val releaseDate: String,
    val rating: String
)
