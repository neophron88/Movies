package com.rasulov.database.all_categories_dao.tuples

import com.rasulov.database.common_models.MovieEntity

class InsertMoviesByGenreTuple(
    val genreId: Int,
    val movies: List<MovieEntity>
)