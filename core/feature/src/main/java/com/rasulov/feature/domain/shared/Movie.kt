package com.rasulov.feature.domain.shared

data class Movie(
    val id: Int,
    val posterUrl: String,
    val backdropUrl: String,
    val title: String,
    val releaseDate: String,
    val rating: String
)
