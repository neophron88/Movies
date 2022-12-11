package com.rasulov.main.presentation.all_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasulov.feature.domain.BaseQuery
import com.rasulov.feature.domain.Movie
import com.rasulov.feature.domain.Resource
import com.rasulov.feature.domain.map
import com.rasulov.feature.presentation.asLanguage
import com.rasulov.feature.utils.CurrentLanguage
import com.rasulov.feature.utils.StringResource
import com.rasulov.main.R
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.models.Category
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.models.Recently
import com.rasulov.main.domain.queries.GenreChangedQuery
import com.rasulov.main.domain.queries.MoviesByGenreQuery
import com.rasulov.main.domain.repository.AllCategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class AllCategoriesViewModel(
    private val stringRes: StringResource,
    private val language: CurrentLanguage,
    private val repository: AllCategoriesRepository
) : ViewModel() {


    fun allCategoriesFlow() = repository
        .getGenresFlow(BaseQuery(language.asLanguage()))
        .map { mapToCategoryList(it) }


    fun loadMoviesByGenre(genre: Genre): Flow<Resource<List<Movie>>> =
        repository.loadMoviesByGenre(
            MoviesByGenreQuery(language.asLanguage(), genre.id, genre.sortBy)
        )


    fun changeGenreSortBy(genreId: Int, sortBy: SortBy) = viewModelScope.launch {
        repository.setGenreSettings(
            GenreChangedQuery(genreId, sortBy)
        )
    }


    fun loadRecentlyMovies(): Flow<Resource<List<Movie>>> =
        repository.loadRecentlyMovies()


    private fun mapToCategoryList(resource: Resource<List<Genre>>): Resource<List<Category>> =
        resource.map {
            mutableListOf<Category>().apply {
                add(Recently(stringRes.getString(R.string.recently)))
                addAll(it)
            }.toList()
        }

}