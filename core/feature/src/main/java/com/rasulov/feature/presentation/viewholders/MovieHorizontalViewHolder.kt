package com.rasulov.feature.presentation.viewholders

import android.view.View
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rasulov.feature.databinding.HorizontalMovieBinding
import com.rasulov.feature.domain.Movie
import com.rasulov.feature.presentation.glide.GlideApp
import com.rasulov.library.rv_adapter_delegate.ItemViewHolder


class MovieHorizontalViewHolder(
    view: View,
    val onMovieClick: OnMovieClick,
) : ItemViewHolder<Movie>(view) {

    private val binding = HorizontalMovieBinding.bind(view)

    init {
        setupClickListeners()
    }


    override fun onBind(item: Movie) = with(binding) {
        item.backdropPath?.let {
            GlideApp.with(binding.root)
                .load("${imageUrlHorizontal}$it")
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(horizontalPoster)
        }
        Unit

    }


    private fun setupClickListeners() =
        binding.root.setOnClickListener { onMovieClick(item.id) }

}