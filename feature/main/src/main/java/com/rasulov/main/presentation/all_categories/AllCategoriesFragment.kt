package com.rasulov.main.presentation.all_categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.main.R
import com.rasulov.main.databinding.FragmentAllCategoriesBinding
import com.rasulov.main.presentation.all_categories.setup.setupAdapter
import com.rasulov.main.presentation.all_categories.setup.setupList
import com.rasulov.ui.ktx.fragment.scope.repeatWhenViewStarted
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter
import com.rasulov.ui.viewbinding_delegate.viewBindings
import kotlinx.coroutines.flow.collectLatest

class AllCategoriesFragment : Fragment(R.layout.fragment_all_categories) {

    internal val binding: FragmentAllCategoriesBinding by viewBindings()
    internal val viewModel: AllCategoriesViewModel by viewModels()
    internal val viewPool = RecyclerView.RecycledViewPool()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = setupAdapter()
        setupList(adapter)
        observeAllGenres(adapter)

    }

    private fun observeAllGenres(adapter: ItemsAdapter) =
        repeatWhenViewStarted {
            viewModel.allCategoriesFlow.collectLatest {
                adapter.submitList(it)
            }
        }

}