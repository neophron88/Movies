package com.rasulov.main.presentation.all_categories.setup.viewholder

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.rasulov.feature.domain.Loading
import com.rasulov.feature.domain.Movie
import com.rasulov.feature.domain.Resource
import com.rasulov.feature.domain.Success
import com.rasulov.library.ktx.primitives.dp
import com.rasulov.library.rv_adapter_delegate.ItemDelegate
import com.rasulov.library.rv_adapter_delegate.ItemsAdapter
import com.rasulov.main.databinding.RecentlyItemBinding
import com.rasulov.main.domain.models.Recently
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

typealias OnLoadRecentlyMovies = () -> Flow<Resource<List<Movie>>>

class RecentlyViewHolder(
    view: View,
    viewLifecycleOwner: LifecycleOwner,
    movieHorizontalItemDelegate: ItemDelegate<Movie>,
    val onLoadRecentlyMovies: OnLoadRecentlyMovies
) : SafeItemViewHolder<Recently>(view, viewLifecycleOwner) {

    private val binding = RecentlyItemBinding.bind(view)
    private val moviesAdapter = ItemsAdapter(movieHorizontalItemDelegate)

    init {
        setupViewPager()
    }

    override fun onBind(item: Recently) {
        super.onBind(item)
        viewHolderScope.safeLaunch {
            onLoadRecentlyMovies().collect {
                when (it) {
                    is Loading -> Unit
                    is Success -> {
                        moviesAdapter.submitList(it.value.take(10))
                        binding.dotsIndicator.isVisible = true
                    }
                    else -> error("$it is unhandled")
                }
            }
        }
    }

    private fun setupViewPager() = with(binding) {
        viewpagerRecently.adapter = moviesAdapter
        viewpagerRecently.offscreenPageLimit = 3
        viewpagerRecently.clipToPadding = false
        viewpagerRecently.clipChildren = false
        viewpagerRecently.setPageTransformer(CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(10.dp(root.context)))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        })

        dotsIndicator.attachTo(viewpagerRecently)
    }

}

