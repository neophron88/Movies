package com.rasulov.main.presentation.all_categories.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.main.databinding.GenreItemBinding
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.queries.FindMoviesBy
import com.rasulov.main.domain.queries.GenreChanged
import com.rasulov.ui.ktx.view.setOnItemSelectedListener
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemViewHolder
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter
import kotlinx.coroutines.*

typealias OnMoreMoviesClick = (genreId: Int) -> Unit
typealias OnSortByChanged = (genreChanged: GenreChanged) -> Unit
typealias OnLoadMovies = suspend (genre: Genre) -> List<Movie>


class GenreViewHolder(
    view: View,
    movieItemDelegate: ItemDelegate<Movie>,
    private val onLoadMovies: OnLoadMovies,
    private val onMoreMoviesClick: OnMoreMoviesClick,
    private val onSortByChanged: OnSortByChanged,
    private val movieViewPool: RecyclerView.RecycledViewPool,
    private val movieLayoutManager: RecyclerView.LayoutManager,
) : ItemViewHolder<Genre>(view) {

    private val viewHolderScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val binding = GenreItemBinding.bind(view)
    private val moviesAdapter = ItemsAdapter(movieItemDelegate)

    init {
        setUpClickListeners()
        setupList()
    }


    override fun onBind(item: Genre) = with(binding) {
        loadMovies()
        genreName.text = item.name
        spinnerSortBy.setSelection(item.sortBy.ordinal)
    }

    private fun loadMovies() = viewHolderScope.launch {
        val findBy = FindMoviesBy(
            sortBy = item.sortBy,
            genreId = item.id
        )
        val list = onLoadMovies(item)
        moviesAdapter.submitList(list)
    }

    override fun unBind() {
        viewHolderScope.cancel()
        moviesAdapter.submitList(listOf())
    }


    private fun setUpClickListeners() = with(binding) {
        spinnerSortBy.setOnItemSelectedListener { _, position ->
            onSortByChanged(GenreChanged(item.id, SortBy.values()[position]))
        }
        morePointer.setOnClickListener { onMoreMoviesClick(item.id) }
    }

    private fun setupList() = with(binding.listGenre) {
        adapter = moviesAdapter
        layoutManager = movieLayoutManager
        setRecycledViewPool(movieViewPool)
        setHasFixedSize(true)
    }

}

