package com.rasulov.main.domain.repository

import com.rasulov.shared.domain.models.Movie
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.queries.GenreChanged
import kotlinx.coroutines.flow.Flow

interface AllCategoriesRepository {

    fun getGenresFlow(): Flow<List<Genre>>

    fun changeGenreSortBy(genre: GenreChanged)

    suspend fun loadMoviesByGenre(genre: Genre):  Flow<List<Movie>>

    suspend fun loadTopRatedMovies():  Flow<List<Movie>>

    suspend fun loadRecentlyMovies():  Flow<List<Movie>>
}