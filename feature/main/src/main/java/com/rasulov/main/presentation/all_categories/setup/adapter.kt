package com.rasulov.main.presentation.all_categories.setup

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasulov.shared.domain.models.Movie
import com.rasulov.shared.presentation.viewholder.MovieViewHolder
import com.rasulov.main.R
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.entities.Recently
import com.rasulov.main.domain.entities.TopRated
import com.rasulov.main.presentation.all_categories.AllCategoriesFragment
import com.rasulov.main.presentation.all_categories.viewholder.GenreViewHolder
import com.rasulov.ui.ktx.primitives.dp
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemDiffUtil
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter


internal fun AllCategoriesFragment.setupAdapter(): ItemsAdapter {

    val genreDelegate = ItemDelegate(
        layout = R.layout.genre_item,
        diffUtil = ItemDiffUtil(itemsTheSamePointer = Genre::id),
        itemViewHolderProducer = { createGenreViewHolder(it) },
    )

    val topRatedDelegate = ItemDelegate(
        layout = R.layout.top_rated_item,
        diffUtil = ItemDiffUtil(itemsTheSamePointer = TopRated::title),
        itemViewHolderProducer = { TODO() },
    )

    val recentlyDelegate = ItemDelegate(
        layout = R.layout.genre_item,
        diffUtil = ItemDiffUtil(itemsTheSamePointer = Recently::title),
        itemViewHolderProducer = { TODO() },
    )


    return ItemsAdapter(genreDelegate, topRatedDelegate, recentlyDelegate)
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
    layout = com.rasulov.shared.R.layout.movie,
    diffUtil = ItemDiffUtil(itemsTheSamePointer = Movie::id),
    itemViewHolderProducer = { view ->
        val posterWidth = 150.dp(requireContext())
        MovieViewHolder(view, posterWidth) { movieId -> navigateToMovieDetailScreen(movieId) }
    }

)
