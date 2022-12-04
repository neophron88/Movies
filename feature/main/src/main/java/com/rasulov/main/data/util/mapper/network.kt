package com.rasulov.main.data.util.mapper

import com.rasulov.feature.data.base.networkdatasource.params.BaseNetworkParams
import com.rasulov.feature.domain.base.enums.Language
import com.rasulov.feature.domain.base.queries.BaseQuery
import com.rasulov.main.data.network_datasource.retrofit_impl.params.ByGenreNetworkParams
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.queries.MoviesByGenreQuery


fun BaseQuery.toBaseParams(page: Int? = null): BaseNetworkParams = BaseNetworkParams(
    language = defineLanguage(language),
    page = page
)


fun MoviesByGenreQuery.toByGenreNetworkParams(page: Int) = ByGenreNetworkParams(
    genreId = this.genreId,
    page = page,
    language = defineLanguage(language),
    sortBy = defineSortBy(sortBy)
)

private fun defineLanguage(language: Language): String =
    when (language) {
        Language.EN -> BaseNetworkParams.EN
        Language.RU -> BaseNetworkParams.RU
        else -> throw IllegalStateException("In network request it's required a certain language")
    }


fun defineSortBy(sortBy: SortBy): String =
    when (sortBy) {
        SortBy.POPULARITY -> ByGenreNetworkParams.POPULARITY
        SortBy.RATING -> ByGenreNetworkParams.RATING
        SortBy.RELEASE_DATE -> ByGenreNetworkParams.RELEASE_DATE
        SortBy.REVENUE -> ByGenreNetworkParams.REVENUE
    }




