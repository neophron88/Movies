package com.rasulov.main.domain

import com.rasulov.main.domain.enums.Language
import com.rasulov.main.domain.enums.SortBy
import java.util.*

data class FindMoviesBy(
    val language: Language = Language.EN,
    val sortBy: SortBy,
    val releaseDate: Date? = null,
    val genreId: Int? = null,
    val page: Int = 1,
)