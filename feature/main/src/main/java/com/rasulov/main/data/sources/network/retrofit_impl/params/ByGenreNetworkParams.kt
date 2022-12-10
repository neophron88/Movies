package com.rasulov.main.data.sources.network.retrofit_impl.params

import androidx.annotation.StringDef
import com.rasulov.feature.data.network.params.BaseNetworkParams

class ByGenreNetworkParams(
    val genreId: Int,
    page: Int,
    @Lang language: String,
    @SortBy val sortBy: String
) : BaseNetworkParams(page, language) {


    companion object {
        const val SORT_BY_KEY = "sort_by"
        const val POPULARITY = "popularity.desc"
        const val RELEASE_DATE = "release_date.desc"
        const val REVENUE = "revenue.desc"
        const val RATING = "vote_average.desc"

        const val WITH_GENRES_KEY = "with_genres"
        const val WITHOUT_GENRES_KEY = "without_genres"
    }

    @StringDef(POPULARITY, RELEASE_DATE, REVENUE, RATING)
    @Retention(AnnotationRetention.SOURCE)
    annotation class SortBy

}