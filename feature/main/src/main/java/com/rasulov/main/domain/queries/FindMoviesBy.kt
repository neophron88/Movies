package com.rasulov.main.domain.queries

import com.rasulov.main.domain.enums.Language
import com.rasulov.main.domain.enums.SortBy

data class FindMoviesBy(
    val language: Language = Language.EN,
    val sortBy: SortBy? = null,
    val year: Int? = null,
    val genreId: Int? = null,
    val page: Int? = null
)