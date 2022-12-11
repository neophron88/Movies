package com.rasulov.feature.presentation.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.rasulov.feature.databinding.VerticalMovieBinding
import com.rasulov.feature.domain.Movie
import com.rasulov.library.ktx.primitives.Dp
import com.rasulov.library.rv_adapter_delegate.ItemViewHolder


class MovieVerticalViewHolder(
    view: View,
    @Dp val posterWidth: Int,
    val onMovieClick: OnMovieClick,
) : ItemViewHolder<Movie>(view) {

    private val binding = VerticalMovieBinding.bind(view)

    init {
        setupClickListeners()
        with(binding) {
            poster.layoutParams = poster.layoutParams.apply { width = posterWidth }
        }
    }


    override fun onBind(item: Movie) = with(binding) {
        item.posterPath?.let {
            Glide.with(binding.root)
                .load("${imageUrl}$it")
                .into(poster)
        }

        title.text = item.title
        releaseDate.text = item.releaseDate
        rating.text = item.rating

    }


    private fun setupClickListeners() =
        binding.root.setOnClickListener { onMovieClick(item.id) }

}