package com.rasulov.main.presentation.all_categories.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.shared.domain.models.Movie
import com.rasulov.main.databinding.GenreItemBinding
import com.rasulov.main.domain.entities.Genre
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.queries.GenreChanged
import com.rasulov.ui.ktx.view.setOnItemSelectedListener
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter

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
) : SafeItemViewHolder<Genre>(view) {

    private val binding = GenreItemBinding.bind(view)
    private val moviesAdapter = ItemsAdapter(movieItemDelegate)

    init {
        setUpClickListeners()
        setupList()
        this.adapterPosition
    }


    override fun onBind(item: Genre) = with(binding) {
        loadMovies(item)
        genreName.text = item.name
        spinnerSortBy.setSelection(item.sortBy.ordinal)

    }

    private fun loadMovies(item: Genre) = viewHolderScope.safeLaunch {
        val list = onLoadMovies(item)
        moviesAdapter.submitList(list)
    }

    override fun unBind() {
        super.unBind()
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

