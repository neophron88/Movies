package com.rasulov.main.domain.repository

import androidx.paging.PagingSource
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.main.domain.entities.Genre

interface CategoryRepository {


    fun moviesByGenre(genre: Genre): PagingSource<Int, Movie>

    fun topRatedMovies(): PagingSource<Int, Movie>

    fun recentlyMovies(): PagingSource<Int, Movie>

}