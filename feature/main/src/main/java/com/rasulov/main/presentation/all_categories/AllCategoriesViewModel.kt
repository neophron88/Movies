package com.rasulov.main.presentation.all_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.feature.presentation.shared.util.StringResource
import com.rasulov.main.R
import com.rasulov.main.domain.entities.Category
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.entities.Recently
import com.rasulov.main.domain.entities.TopRated
import com.rasulov.main.domain.queries.GenreChanged
import com.rasulov.main.domain.repository.AllCategoriesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AllCategoriesViewModel(
    private val stringRes: StringResource,
    private val repository: AllCategoriesRepository
) : ViewModel() {


    val allCategoriesFlow = repository
        .getGenresFlow()
        .map { convertToCategoryList(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())


    suspend fun loadMoviesByGenre(genre: Genre): List<Movie> {
        TODO()
    }

    fun changeGenreSortBy(genre: GenreChanged) {
        TODO("Not yet implemented")
    }

    suspend fun loadTopRatedMovies(): List<Movie> {
        TODO()
    }

    suspend fun loadRecentlyMovies(): List<Movie> {
        TODO()
    }

    private fun convertToCategoryList(genres: List<Genre>): List<Category> {
        return mutableListOf<Category>().apply {
            add(TopRated(stringRes.getString(R.string.top_rated)))
            add(Recently(stringRes.getString(R.string.recently)))
            addAll(genres)
        }

    }

}