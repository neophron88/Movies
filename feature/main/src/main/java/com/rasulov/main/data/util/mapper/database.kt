package com.rasulov.main.data.util.mapper

import com.rasulov.database.all_categories_dao.models.GenreEntity
import com.rasulov.database.all_categories_dao.models.GenreSettingsEntity
import com.rasulov.database.all_categories_dao.tuples.GenreWithSettingsTuple
import com.rasulov.database.all_categories_dao.tuples.InsertGenresTuple
import com.rasulov.database.all_categories_dao.tuples.InsertMoviesByGenreTuple
import com.rasulov.database.common_models.MovieEntity
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.queries.GenreChangedQuery
import com.rasulov.network.all_categories_service.models.NetworkGenre
import com.rasulov.network.common_models.NetworkMovie


fun List<NetworkGenre>.toInsertGenresTuple() = InsertGenresTuple(
    genres = this.map { it.toGenreEntity() }
)

private fun NetworkGenre.toGenreEntity() = GenreEntity(
    id = this.id,
    name = this.name
)

fun List<NetworkMovie>.toInsertMoviesByGenreTuple(genreId: Int) = InsertMoviesByGenreTuple(
    genreId = genreId,
    movies = this.map { it.toMovieEntity(genreId) }
)

private fun NetworkMovie.toMovieEntity(genreId: Int? = null) = MovieEntity(
    id = this.id,
    genreId = genreId,
    title = this.title,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    rating = this.rating
)

fun GenreChangedQuery.toGenreSettingsEntity() = GenreSettingsEntity(
    genreId = this.genreId,
    sortBy = this.sortBy.ordinal
)

fun List<MovieEntity>.toMovies() = this.map { it.toMovie() }

private fun MovieEntity.toMovie() = Movie(
    id = this.id,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    title = this.title,
    releaseDate = this.releaseDate,
    rating = this.rating.toString()
)


fun List<GenreWithSettingsTuple>.toGenres() = this.map { it.toGenre() }

fun GenreWithSettingsTuple.toGenre() = Genre(
    id = this.genre.id,
    name = this.genre.name,
    sortBy = this.settings?.sortBy?.let { SortBy.values()[it] } ?: SortBy.POPULARITY
)
