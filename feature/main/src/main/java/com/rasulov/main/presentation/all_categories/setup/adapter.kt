package com.rasulov.main.presentation.all_categories.setup

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.feature.domain.Movie
import com.rasulov.feature.presentation.viewholders.MovieHorizontalViewHolder
import com.rasulov.feature.presentation.viewholders.MovieVerticalViewHolder
import com.rasulov.library.ktx.primitives.dp
import com.rasulov.library.rv_adapter_delegate.ItemDelegate
import com.rasulov.library.rv_adapter_delegate.ItemDiffUtil
import com.rasulov.library.rv_adapter_delegate.ItemsAdapter
import com.rasulov.main.R
import com.rasulov.main.domain.models.Genre
import com.rasulov.main.domain.models.Recently
import com.rasulov.main.presentation.all_categories.AllCategoriesFragment
import com.rasulov.main.presentation.all_categories.setup.adapters.SpinnerSortByAdapter
import com.rasulov.main.presentation.all_categories.setup.viewholder.GenreViewHolder
import com.rasulov.main.presentation.all_categories.setup.viewholder.RecentlyViewHolder


internal fun AllCategoriesFragment.setupAdapter(): ItemsAdapter {

    val viewPool = RecyclerView.RecycledViewPool()

    val genreDelegate = ItemDelegate(
        layout = R.layout.genre_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = Genre::id),
        VHProducer = { createGenreViewHolder(it, viewPool) },
    )

    val recentlyDelegate = ItemDelegate(
        layout = R.layout.recently_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = Recently::title),
        VHProducer = { createRecentlyViewHolder(it) },
    )

    return ItemsAdapter(genreDelegate, recentlyDelegate).also {
        it.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
}


private fun AllCategoriesFragment.createGenreViewHolder(
    view: View,
    viewPool: RecyclerView.RecycledViewPool
) = GenreViewHolder(
    view = view,
    viewLifecycleOwner = viewLifecycleOwner,
    onLoadMovies = { viewModel.loadMoviesByGenre(it) },
    onMoreMoviesClick = { navigateToGenreScreen(it) },
    onSortByChanged = { genreId, sortBy -> viewModel.changeGenreSortBy(genreId, sortBy) },
    onError = { errorType, errorTask -> showErrorBarAndAddErrorTask(errorType, errorTask) },
    onRemoveError = { errorTasks.remove(it) },
    movieViewPool = viewPool,
    movieVerticalItemDelegate = createVerticalMovieItemDelegate(),
    movieLayoutManager = LinearLayoutManager(
        requireContext(),
        LinearLayoutManager.HORIZONTAL,
        false
    ),
    spinnerSortByAdapter = SpinnerSortByAdapter(requireContext())

)


private fun AllCategoriesFragment.createVerticalMovieItemDelegate() = ItemDelegate(
    layout = com.rasulov.feature.R.layout.vertical_movie,
    diffUtil = ItemDiffUtil(itemsTheSameValue = Movie::id),
    VHProducer = {
        MovieVerticalViewHolder(
            view = it,
            posterWidth = 130.dp(requireContext()),
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


