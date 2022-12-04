package com.rasulov.feature.presentation.shared.viewholders

import android.view.View
import com.rasulov.common.ktx.primitives.Dp
import com.rasulov.feature.R
import com.rasulov.feature.databinding.VerticalMovieBinding
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.ui.rv_adapter_delegate.ItemViewHolder


class MovieVerticalViewHolder(
    view: View,
    @Dp val posterWidth: Int,
    val onMovieClick: OnMovieClick,
) : ItemViewHolder<Movie>(view) {

    private val binding = VerticalMovieBinding.bind(view)

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