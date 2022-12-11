package com.rasulov.main.data.util.mapper

import com.rasulov.feature.data.network.params.BaseNetworkParams
import com.rasulov.feature.domain.BaseQuery
import com.rasulov.feature.domain.Language
import com.rasulov.main.data.sources.network.retrofit_impl.params.ByGenreNetworkParams
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.queries.MoviesByGenreQuery


fun BaseQuery.toBaseParams(): BaseNetworkParams = BaseNetworkParams(
    language = defineLanguage(language),
)


fun MoviesByGenreQuery.toByGenreNetworkParams(page: Int) = ByGenreNetworkParams(
    genreId = this.genreId,
    language = defineLanguage(language),
    sortBy = defineSortBy(sortBy)
)

private fun defineLanguage(language: Language): String =
    when (language) {
        Language.RU -> BaseNetworkParams.RU
        else -> BaseNetworkParams.EN
    }


private fun defineSortBy(sortBy: SortBy): String =
    when (sortBy) {
        SortBy.POPULARITY -> ByGenreNetworkParams.POPULARITY
        SortBy.RATING -> ByGenreNetworkParams.RATING
        SortBy.RELEASE_DATE -> ByGenreNetworkParams.RELEASE_DATE
        SortBy.REVENUE -> ByGenreNetworkParams.REVENUE
    }




