package com.rasulov.feature.presentation.shared.movie_delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rasulov.feature.databinding.MovieBinding
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemViewHolder

typealias OnMovieClick = (id: Int) -> Unit

class MovieItemDelegate(
    val onMovieClick: OnMovieClick
) : ItemDelegate<Movie>(Movie::class) {
    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<Movie> {
        TODO("Not yet implemented")
    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        TODO("Not yet implemented")
    }
}

class MovieHolder(
    private val binding: MovieBinding
) : ItemViewHolder<Movie>(binding.root) {

    override fun bind(item: Movie) {
        TODO("Not yet implemented")
    }

}