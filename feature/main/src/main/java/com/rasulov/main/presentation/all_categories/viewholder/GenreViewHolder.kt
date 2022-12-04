package com.rasulov.main.presentation.all_categories.viewholder

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.common.ktx.view.setOnItemSelectedListener
import com.rasulov.feature.domain.base.*
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.feature.presentation.base.Task
import com.rasulov.main.databinding.GenreItemBinding
import com.rasulov.main.domain.enums.SortBy
import com.rasulov.main.domain.models.Genre
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

typealias OnMoreMoviesClick = (genreId: Int) -> Unit
typealias OnSortByChanged = suspend (genreId: Int, sortBy: SortBy) -> Unit
typealias OnLoadMovies = (genre: Genre) -> Flow<Resource<List<Movie>>>
typealias OnErrorOccurred = (type: ErrorType, task: Task) -> Unit
typealias OnClearErrorOccurredTask = (Task) -> Unit


class GenreViewHolder(
    view: View,
    viewLifecycleOwner: LifecycleOwner,
    private val onLoadMovies: OnLoadMovies,
    private val onMoreMoviesClick: OnMoreMoviesClick,
    private val onSortByChanged: OnSortByChanged,
    private val onError: OnErrorOccurred,
    private val onClearError: OnClearErrorOccurredTask,
    private val movieViewPool: RecyclerView.RecycledViewPool,
    movieVerticalItemDelegate: ItemDelegate<Movie>,
    private val movieLayoutManager: RecyclerView.LayoutManager,
) : SafeItemViewHolder<Genre>(view, viewLifecycleOwner) {

    private val binding = GenreItemBinding.bind(view)
    private val moviesAdapter = ItemsAdapter(movieVerticalItemDelegate)

    private val onErrorTask: Task = {
        cancelJobs()
        loadMovies(item)
    }

    init {
        setUpClickListeners()
        setupList()
    }


    override fun onBind(item: Genre) = with(binding) {
        super.onBind(item)
        onClearError(onErrorTask)
        loadMovies(item)
        genreName.text = item.name
        spinnerSortBy.setSelection(item.sortBy.ordinal)
    }

    override fun unBind() {
        super.unBind()
        onClearError(onErrorTask)
    }


    private fun loadMovies(item: Genre) = viewHolderScope.safeLaunch {
        onLoadMovies(item).collect {
            when (it) {
                is Loading -> Unit
                is Success -> moviesAdapter.submitList(it.value)
                is Error -> onError(it.type, onErrorTask)
            }
        }
    }


    private fun setUpClickListeners() = with(binding) {
        spinnerSortBy.setOnItemSelectedListener { _, position ->
            viewHolderScope.launch {
                onSortByChanged(item.id, SortBy.values()[position])
            }
        }
        morePointer.setOnClickListener { onMoreMoviesClick(item.id) }
    }

    private fun setupList() = with(binding.listGenre) {
        adapter = moviesAdapter
        layoutManager = movieLayoutManager
        setRecycledViewPool(movieViewPool)
    }

}

