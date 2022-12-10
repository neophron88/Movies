package com.rasulov.main.domain.queries

import com.rasulov.feature.domain.BaseQuery
import com.rasulov.feature.domain.Language
import com.rasulov.main.domain.enums.SortBy

class MoviesByGenreQuery(
    language: Language,
    val genreId: Int,
    val sortBy: SortBy
) : BaseQuery(language)