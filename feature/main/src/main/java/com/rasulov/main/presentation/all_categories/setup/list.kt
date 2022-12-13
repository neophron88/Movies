package com.rasulov.main.presentation.all_categories.setup

import androidx.recyclerview.widget.LinearLayoutManager
import com.rasulov.main.presentation.all_categories.AllCategoriesFragment

internal fun AllCategoriesFragment.setupList(
) = with(binding.listAllCategories) {
    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    adapter = itemsAdapter
    itemAnimator = null
}