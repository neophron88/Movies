package com.rasulov.main.domain.queries

import com.rasulov.main.domain.enums.SortBy

data class GenreChanged(
    val id: Int,
    val sortBy: SortBy
)