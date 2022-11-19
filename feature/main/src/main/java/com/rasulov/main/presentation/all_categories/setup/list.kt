package com.rasulov.main.presentation.all_categories.setup

import androidx.recyclerview.widget.LinearLayoutManager
import com.rasulov.main.presentation.all_categories.AllCategoriesFragment
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter

internal fun AllCategoriesFragment.setupList(
    itemsAdapter: ItemsAdapter
) = with(binding.listAllCategories) {
    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    adapter = itemsAdapter
}