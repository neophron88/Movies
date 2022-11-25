package com.rasulov.main.data.sources.remote

import com.rasulov.feature.domain.shared.Movie
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.queries.GenreChanged
import kotlinx.coroutines.flow.Flow

interface AllCategoriesRemoteDataSource {

    suspend fun loadGenresIfCurrentOutdated(): List<Genre>

    suspend fun loadMoviesByGenreIfCurrentOutdated(genre: Genre): List<Movie>

    suspend fun loadTopRatedMoviesIfCurrentOutdated(): List<Movie>

}