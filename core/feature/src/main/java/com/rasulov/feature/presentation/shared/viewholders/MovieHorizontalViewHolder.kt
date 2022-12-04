package com.rasulov.feature.presentation.shared.viewholders

import android.view.View
import com.rasulov.feature.R
import com.rasulov.feature.databinding.VerticalMovieBinding
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.ui.rv_adapter_delegate.ItemViewHolder


class MovieHorizontalViewHolder(
    view: View,
    val onMovieClick: OnMovieClick,
) : ItemViewHolder<Movie>(view) {

    private val binding = VerticalMovieBinding.bind(view)

    init { setupClickListeners() }


    override fun onBind(item: Movie) = with(binding) {
        poster.setImageResource(R.drawable.test_image)
    }


    private fun setupClickListeners() =
        binding.root.setOnClickListener { onMovieClick(item.id) }

}