package com.rasulov.feature.domain.shared

import com.rasulov.feature.R


data class Movie(
    val id: Int,
    val poster: Int = R.drawable.test_image,
    val title: String = "Avengers",
    val rate: Double = 7.9,
    val releaseDate: String = "2024-07-19"
)
