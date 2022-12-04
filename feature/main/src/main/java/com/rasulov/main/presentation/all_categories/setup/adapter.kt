package com.rasulov.main.presentation.all_categories.setup

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasulov.common.ktx.primitives.dp
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.feature.presentation.shared.viewholders.MovieHorizontalViewHolder
import com.rasulov.feature.presentation.shared.viewholders.MovieVerticalViewHolder
import com.rasulov.main.R
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.models.Recently
import com.rasulov.main.presentation.all_categories.AllCategoriesFragment
import com.rasulov.main.presentation.all_categories.showErrorBarAndAddErrorTask
import com.rasulov.main.presentation.all_categories.viewholder.GenreViewHolder
import com.rasulov.main.presentation.all_categories.viewholder.RecentlyViewHolder
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemDiffUtil
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter


internal fun AllCategoriesFragment.setupAdapter(): ItemsAdapter {

    val genreDelegate = ItemDelegate(
        layout = R.layout.genre_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = Genre::id),
        VHProducer = { createGenreViewHolder(it) },
    )

    val recentlyDelegate = ItemDelegate(
        layout = R.layout.recently_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = Recently::title),
        VHProducer = { createRecentlyViewHolder(it) },
    )

    return ItemsAdapter(genreDelegate, recentlyDelegate)
}


private fun AllCategoriesFragment.createGenreViewHolder(view: View) = GenreViewHolder(
    view = view,
    viewLifecycleOwner = viewLifecycleOwner,
    onLoadMovies = { viewModel.loadMoviesByGenre(it) },
    onMoreMoviesClick = { navigateToCategoryScreen(it) },
    onSortByChanged = { genreId, sortBy -> viewModel.changeGenreSortBy(genreId, sortBy) },
    onError = { errorType, errorTask -> showErrorBarAndAddErrorTask(errorType, errorTask) },
    onClearError = { errorTasks.remove(it) },
    movieViewPool = viewPool,
    movieVerticalItemDelegate = createVerticalMovieItemDelegate(),
    movieLayoutManager = LinearLayoutManager(
        requireContext(),
        LinearLayoutManager.HORIZONTAL,
        false
    )
)


private fun AllCategoriesFragment.createVerticalMovieItemDelegate() = ItemDelegate(
    layout = com.rasulov.feature.R.layout.vertical_movie,
    diffUtil = ItemDiffUtil(itemsTheSameValue = Movie::id),
    VHProducer = {
        MovieVerticalViewHolder(
            view = it,
            posterWidth = 150.dp(requireContext()),
            onMovieClick = { movieId -> navigateToMovieDetailScreen(movieId) }
        )
    }
)


private fun AllCategoriesFragment.createRecentlyViewHolder(view: View) = RecentlyViewHolder(
    view = view,
    viewLifecycleOwner = viewLifecycleOwner,
    onLoadRecentlyMovies = { viewModel.loadRecentlyMovies() },
    movieHorizontalItemDelegate = createHorizontalMovieItemDelegate(),
)

private fun AllCategoriesFragment.createHorizontalMovieItemDelegate() = ItemDelegate(
    layout = com.rasulov.feature.R.layout.horizontal_movie,
    diffUtil = ItemDiffUtil(itemsTheSameValue = Movie::id),
    VHProducer = {
        MovieHorizontalViewHolder(
            view = it,
            onMovieClick = { movieId -> navigateToMovieDetailScreen(movieId) }
        )
    }

)


