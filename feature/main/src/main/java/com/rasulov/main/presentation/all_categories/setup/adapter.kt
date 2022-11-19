package com.rasulov.main.presentation.all_categories.setup

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasulov.feature.R.*
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.feature.presentation.shared.movie_delegate.MovieViewHolder
import com.rasulov.main.R
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.entities.TopRated
import com.rasulov.main.presentation.all_categories.AllCategoriesFragment
import com.rasulov.main.presentation.all_categories.viewholder.GenreViewHolder
import com.rasulov.ui.ktx.primitives.dp
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemDiffUtil
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter


internal fun AllCategoriesFragment.setupAdapter(): ItemsAdapter {

    val genreDelegate = ItemDelegate(
        itemClass = Genre::class,
        layout = R.layout.genre_item,
        diffUtil = ItemDiffUtil(itemsTheSamePointer = Genre::id),
        itemViewHolderProducer = { createGenreViewHolder(it) },
    )

    val topRatedDelegate = ItemDelegate(
        itemClass = TopRated::class,
        layout = R.layout.top_rated_item,
        diffUtil = ItemDiffUtil(itemsTheSamePointer = TopRated::title),
        itemViewHolderProducer = { TODO() },
    )

    val recentlyWatchedDelegate = ItemDelegate(
        itemClass = Genre::class,
        layout = R.layout.genre_item,
        diffUtil = ItemDiffUtil(itemsTheSamePointer = Genre::id),
        itemViewHolderProducer = { createGenreViewHolder(it) },
    )


    return ItemsAdapter(genreDelegate, topRatedDelegate, recentlyWatchedDelegate)
}


private fun AllCategoriesFragment.createGenreViewHolder(view: View) = GenreViewHolder(
    view = view,
    movieItemDelegate = createMovieItemDelegate(),
    onLoadMovies = { viewModel.loadMoviesByGenre(it) },
    onMoreMoviesClick = { navigateToCategoryScreen(it) },
    onSortByChanged = { viewModel.changeGenreSortBy(it) },
    movieViewPool = viewPool,
    movieLayoutManager = LinearLayoutManager(
        requireContext(),
        LinearLayoutManager.HORIZONTAL,
        false
    )
)


private fun AllCategoriesFragment.createMovieItemDelegate() = ItemDelegate(
    itemClass = Movie::class,
    layout = layout.movie,
    diffUtil = ItemDiffUtil(itemsTheSamePointer = Movie::id),
    itemViewHolderProducer = { view ->
        val posterWidth = 150.dp(requireContext())
        MovieViewHolder(view, posterWidth) { movieId -> navigateToMovieDetailScreen(movieId) }
    }

)
