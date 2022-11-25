package com.rasulov.main.data.sources.local

import com.rasulov.feature.domain.shared.Movie
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.queries.GenreChanged
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

interface AllCategoriesLocalDataSource {

    fun getGenresFlow(): Flow<List<Genre>>

    suspend fun updateGenres(genres: List<Genre>)

    fun changeGenre(genre: GenreChanged)


    fun getMoviesByGenreFlow(genre: Genre): Flow<List<Movie>>

    suspend fun updateMoviesByGenre(movies: List<Movie>, genre: Genre)


    fun getTopRatedMoviesFlow(): Flow<List<Movie>>

    suspend fun updateTopRatedMovies(movies: List<Movie>)


    fun getRecentlyMoviesFlow(): Flow<List<Movie>>

}
