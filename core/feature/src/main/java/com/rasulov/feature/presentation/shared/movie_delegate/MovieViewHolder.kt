package com.rasulov.feature.presentation.shared.movie_delegate

import android.view.View
import com.rasulov.feature.R
import com.rasulov.feature.databinding.MovieBinding
import com.rasulov.feature.domain.shared.Movie
import com.rasulov.ui.ktx.primitives.Dp
import com.rasulov.ui.rv_adapter_delegate.ItemViewHolder

typealias OnMovieClick = (movieId: Int) -> Unit

class MovieViewHolder(
    view: View,
    @Dp val posterWidth: Int,
    val onMovieClick: OnMovieClick,
) : ItemViewHolder<Movie>(view) {

    private val binding = MovieBinding.bind(view)

    init { setupClickListeners() }


    override fun onBind(item: Movie) = with(binding) {
        poster.layoutParams = poster.layoutParams.apply { width = posterWidth }
        poster.setImageResource(R.drawable.test_image)
        title.text = item.title
        releaseDate.text = item.releaseDate
        rating.text = item.rating
    }


    private fun setupClickListeners() =
        binding.root.setOnClickListener { onMovieClick(item.id) }

}