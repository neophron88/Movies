package com.rasulov.database.all_genres_dao.tuples

import com.rasulov.database.common_models.MovieEntity

class InsertMoviesByGenreTuple(
    val genreId: Int,
    val movies: List<MovieEntity>
)