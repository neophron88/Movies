package com.rasulov.main.presentation.all_categories.setup.viewholder

import android.view.View
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.feature.domain.*
import com.rasulov.feature.presentation.Task
import com.rasulov.library.ktx.view.setOnItemSelectedListener
import com.rasulov.library.rv_adapter_delegate.ItemDelegate
import com.rasulov.library.rv_adapter_delegate.ItemsAdapter
import com.rasulov.main.databinding.GenreItemBinding
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.models.Genre
import kotlinx.coroutines.flow.Flow

typealias OnMoreMoviesClick = (genreId: Int) -> Unit
typealias OnSortByChanged = (genreId: Int, sortBy: SortBy) -> Unit
typealias OnLoadMovies = (genre: Genre) -> Flow<Resource<List<Movie>>>
typealias OnErrorTask = (type: ErrorType, task: Task) -> Unit
typealias OnRemoveErrorTask = (Task) -> Unit


class GenreViewHolder(
    view: View,
    viewLifecycleOwner: LifecycleOwner,
    private val onLoadMovies: OnLoadMovies,
    private val onMoreMoviesClick: OnMoreMoviesClick,
    private val onSortByChanged: OnSortByChanged,
    private val onError: OnErrorTask,
    private val onRemoveError: OnRemoveErrorTask,
    private val movieViewPool: RecyclerView.RecycledViewPool,
    movieVerticalItemDelegate: ItemDelegate<Movie>,
    private val movieLayoutManager: RecyclerView.LayoutManager,
    private val spinnerSortByAdapter: BaseAdapter
) : SafeItemViewHolder<Genre>(view, viewLifecycleOwner) {

    private val binding = GenreItemBinding.bind(view)
    private val moviesAdapter = ItemsAdapter(movieVerticalItemDelegate)

    private val onErrorTask: Task = {
        cancelJobs()
        loadMovies(item)
    }

    init {
        setUpClickListeners()
        setupViews()
    }


    override fun onBind(item: Genre) = with(binding) {
        super.onBind(item)
        onRemoveError(onErrorTask)
        loadMovies(item)
        genreName.text = item.name
        spinnerSortBy.setSelection(item.sortBy.ordinal)
    }

    override fun unBind() {
        super.unBind()
        onRemoveError(onErrorTask)
        moviesAdapter.submitList(emptyList())
    }


    private fun loadMovies(item: Genre) = viewHolderScope.safeLaunch {
        onLoadMovies(item).collect {
            when (it) {
                is Loading -> {
                    binding.shimmerLayout.shimmerGenre.startShimmer()
                    binding.shimmerLayout.shimmerGenre.isVisible = true
                    binding.listGenre.isVisible = false

                }
                is Success -> {
                    moviesAdapter.submitList(it.value)

                    binding.shimmerLayout.shimmerGenre.stopShimmer()
                    binding.shimmerLayout.shimmerGenre.isVisible = false
                    binding.listGenre.isVisible = true

                }
                is Error -> {
                    onError(it.type, onErrorTask)
                }
            }
        }
    }


    private fun setUpClickListeners() = with(binding) {
        spinnerSortBy.setOnItemSelectedListener { _, position ->
            val selectedSortBy = SortBy.values()[position]
            if (item.sortBy != selectedSortBy) onSortByChanged(item.id, selectedSortBy)
        }
        morePointer.setOnClickListener { onMoreMoviesClick(item.id) }
    }

    private fun setupViews() {
        with(binding.listGenre) {
            adapter = moviesAdapter
            itemAnimator = null
            layoutManager = movieLayoutManager
            setRecycledViewPool(movieViewPool)
        }

        with(binding.spinnerSortBy) {
            adapter = spinnerSortByAdapter
        }
    }

}

