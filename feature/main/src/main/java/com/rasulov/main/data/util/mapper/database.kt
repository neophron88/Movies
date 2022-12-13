package com.rasulov.main.data.util.mapper

import com.rasulov.database.all_genres_dao.models.GenreEntity
import com.rasulov.database.all_genres_dao.models.GenreSettingsEntity
import com.rasulov.database.all_genres_dao.tuples.GenreWithSettingsTuple
import com.rasulov.database.all_genres_dao.tuples.InsertGenresTuple
import com.rasulov.database.all_genres_dao.tuples.InsertMoviesByGenreTuple
import com.rasulov.database.common_models.MovieEntity
import com.rasulov.feature.domain.Movie
import com.rasulov.feature.utils.changeDateFormat
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.queries.GenreChangedQuery
import com.rasulov.network.all_genres_service.models.NetworkGenre
import com.rasulov.network.common_models.NetworkMovie
import java.util.*


fun List<NetworkGenre>.toInsertGenresTuple() = InsertGenresTuple(
    genres = this.map { it.toGenreEntity() }
)

private fun NetworkGenre.toGenreEntity() = GenreEntity(
    id = id,
    name = name.firstToUpperCase()
)

private fun String.firstToUpperCase(): String {
    if (length == 0) return this
    val first = this[0]
    val other = this.substring(1)
    return first.uppercase() + other
}

fun List<NetworkMovie>.toInsertMoviesByGenreTuple(genreId: Int) = InsertMoviesByGenreTuple(
    genreId = genreId,
    movies = this.map { it.toMovieEntity(genreId) }
)

private fun NetworkMovie.toMovieEntity(genreId: Int) = MovieEntity(
    id = 0,
    movieId = this.id,
    genreId = genreId,
    title = this.title,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate.changeDateFormat("yyyy-MM-dd","MMM d, yyyy"),
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

private fun GenreWithSettingsTuple.toGenre() = Genre(
    id = this.genre.id,
    name = this.genre.name,
    sortBy = this.settings?.sortBy?.let { SortBy.values()[it] } ?: SortBy.POPULARITY
)
