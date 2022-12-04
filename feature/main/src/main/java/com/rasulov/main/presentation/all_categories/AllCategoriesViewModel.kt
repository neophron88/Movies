package com.rasulov.main.presentation.all_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasulov.common.CurrentLanguage
import com.rasulov.common.StringResource
import com.rasulov.feature.domain.base.Resource
import com.rasulov.feature.domain.base.Success
import com.rasulov.feature.domain.base.enums.Language
import com.rasulov.feature.domain.base.map
import com.rasulov.feature.domain.base.queries.BaseQuery
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.feature.presentation.shared.asLanguage
import com.rasulov.main.R
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.models.Category
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.models.Recently
import com.rasulov.main.domain.queries.GenreChangedQuery
import com.rasulov.main.domain.queries.MoviesByGenreQuery
import com.rasulov.main.domain.repository.AllCategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class AllCategoriesViewModel(
    private val stringRes: StringResource,
    private val language: CurrentLanguage,
    private val repository: AllCategoriesRepository
) : ViewModel() {


    val allCategoriesFlow = repository
        .getGenresFlow(BaseQuery(Language.EN))
        .map { mapToCategoryList(it) }
        .shareIn(viewModelScope, SharingStarted.Lazily, 1)


    fun loadMoviesByGenre(genre: Genre): Flow<Resource<List<Movie>>> =
        repository.loadMoviesByGenre(
            MoviesByGenreQuery(language.asLanguage(), genre.id, genre.sortBy)
        )


    suspend fun changeGenreSortBy(genreId: Int, sortBy: SortBy) =
        repository.setGenreSettings(
            GenreChangedQuery(genreId, sortBy)
        )


    fun loadRecentlyMovies(): Flow<Resource<List<Movie>>> =
        repository.loadRecentlyMovies()


    private fun mapToCategoryList(resource: Resource<List<Genre>>) =
        resource.map {
            mutableListOf<Category>().apply {
                add(Recently(stringRes.getString(R.string.recently)))
                addAll(it)
            }.toList()
        }

}