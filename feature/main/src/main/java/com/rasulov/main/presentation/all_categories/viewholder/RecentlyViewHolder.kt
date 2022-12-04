package com.rasulov.main.presentation.all_categories.viewholder

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.rasulov.feature.domain.base.Error
import com.rasulov.feature.domain.base.Loading
import com.rasulov.feature.domain.base.Resource
import com.rasulov.feature.domain.base.Success
import com.rasulov.feature.domain.shared.models.Movie
import com.rasulov.main.databinding.GenreItemBinding
import com.rasulov.main.databinding.RecentlyItemBinding
import com.rasulov.main.domain.models.Recently
import com.rasulov.ui.rv_adapter_delegate.ItemDelegate
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter
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
                    is Success -> moviesAdapter.submitList(it.value)
                    else -> error("$it is unhandled")
                }
            }
        }
    }

    private fun setupViewPager() = with(binding) {
        viewpagerRecently.adapter = moviesAdapter
        viewpagerRecently.offscreenPageLimit = 3
        viewpagerRecently.setPageTransformer(CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
        })
    }

}

