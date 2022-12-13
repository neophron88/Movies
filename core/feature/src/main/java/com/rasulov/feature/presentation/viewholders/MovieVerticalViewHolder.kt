package com.rasulov.feature.presentation.viewholders

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rasulov.feature.R
import com.rasulov.feature.databinding.VerticalMovieBinding
import com.rasulov.feature.domain.Movie
import com.rasulov.feature.presentation.glide.GlideApp
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
        }
    }


    override fun onBind(item: Movie) = with(binding) {
        item.posterPath?.let {

            GlideApp.with(binding.root)
                .load("${imageUrl}$it")
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(poster)
        }

        title.text = item.title
        releaseDate.text = item.releaseDate
        rating.text = item.rating

    }

    override fun unBind() {
        super.unBind()
        binding.poster.setImageResource(android.R.color.transparent)
    }


    private fun setupClickListeners() =
        binding.root.setOnClickListener { onMovieClick(item.id) }

}