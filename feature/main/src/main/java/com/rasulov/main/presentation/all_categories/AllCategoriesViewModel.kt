package com.rasulov.main.presentation.all_categories

import androidx.lifecycle.ViewModel
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.main.domain.entities.Category
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.queries.FindMoviesBy
import com.rasulov.main.domain.queries.GenreChanged
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AllCategoriesViewModel : ViewModel() {


    val allCategoriesFlow: Flow<List<Category>> = flow { TODO() }

    suspend fun loadMoviesByGenre(genre: Genre): List<Movie> {
        TODO()
    }

    fun changeGenreSortBy(genre: GenreChanged) {
        TODO("Not yet implemented")
    }

    suspend fun loadTopRatedMovies(): List<Movie> {
        TODO()
    }

    suspend fun loadRecentlyWatchedMovies(): List<Movie> {
        TODO()
    }


}