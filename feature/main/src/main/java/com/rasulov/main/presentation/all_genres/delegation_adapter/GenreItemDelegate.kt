package com.rasulov.main.presentation.all_genres.delegation_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.feature.presentation.shared.movie_delegate.MovieItemDelegate
import com.rasulov.main.databinding.GenreItemBinding
import com.rasulov.main.domain.FindMoviesBy
import com.rasulov.main.domain.Genre
import com.rasulov.ui.ktx.view.setOnItemSelectedListener
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemViewHolder
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter
import com.rasulov.ui.rv_adapter_delegate.MediatorItemDelegate
import kotlinx.coroutines.*

typealias OnMoreMoviesClick = (genreId: Int) -> Unit
typealias OnLoadMovies = suspend (findBy: FindMoviesBy) -> List<Movie>

class GenreItemDelegate(
    val onMoreMoviesClick: OnMoreMoviesClick,
    val onLoadMovies: OnLoadMovies,
    val movieItemDelegate: MovieItemDelegate,
    val viewPool: RecyclerView.RecycledViewPool,
    val mLayoutManager: RecyclerView.LayoutManager
) : ItemDelegate<Genre>(Genre::class) {

    override fun createViewHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ): ItemViewHolder<Genre> {
        val binding = GenreItemBinding.inflate(inflater, parent, false)
        return GenreHolder(binding)
    }

    override fun areItemsTheSame(
        oldItem: Genre, newItem: Genre
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Genre, newItem: Genre
    ): Boolean = oldItem == newItem


    inner class GenreHolder(
        private val binding: GenreItemBinding
    ) : ItemViewHolder<Genre>(binding.root) {

        private val viewHolderScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        private var adapter: ItemsAdapter<Movie>? = null

        init {
            setUpClickListeners()
            setupList()
        }

        private fun setUpClickListeners() = with(binding) {
            spinnerSortBy.setOnItemSelectedListener { _, position ->

            }
        }

        private fun setupList() = with(binding.listGenre) {
            val mediator = MediatorItemDelegate.Builder<Movie>()
                .addItemDelegate(movieItemDelegate)
                .build()

            val itemsAdapter = ItemsAdapter(mediator)
            layoutManager = mLayoutManager
            setRecycledViewPool(viewPool)
            adapter = itemsAdapter
        }


        override fun onBind(item: Genre) = with(binding) {
            genreName.text = item.name
            spinnerSortBy.setSelection(item.sortBy.ordinal)
            loadMovies()
            Unit
        }

        private fun loadMovies() = viewHolderScope.launch {
            val findBy = FindMoviesBy(
                sortBy = item.sortBy,
                genreId = item.id,
            )
            val list = onLoadMovies.invoke(findBy)
            adapter?.submitList(list)
        }

        override fun unBind() {
            viewHolderScope.cancel()
            adapter?.submitList(listOf())
        }

    }
}

