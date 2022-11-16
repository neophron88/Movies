package com.rasulov.main.domain

import com.rasulov.main.domain.enums.SortBy

data class Genre(
    val id: Int,
    val name: String,
    val sortBy: SortBy
)
