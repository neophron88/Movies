package com.rasulov.main.domain.repository

import com.rasulov.feature.domain.base.Resource
import com.rasulov.feature.domain.base.queries.BaseQuery
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.queries.GenreChangedQuery
import com.rasulov.main.domain.queries.MoviesByGenreQuery
import kotlinx.coroutines.flow.Flow

interface AllCategoriesRepository {

    fun getGenresFlow(query: BaseQuery): Flow<Resource<List<Genre>>>

    suspend fun setGenreSettings(query: GenreChangedQuery)

    fun loadMoviesByGenre(query: MoviesByGenreQuery): Flow<Resource<List<Movie>>>

    fun loadRecentlyMovies(): Flow<Resource<List<Movie>>>

}