package com.rasulov.main.presentation.all_categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.rasulov.common.ktx.fragment.scope.viewLifeCycleScope
import com.rasulov.common.ktx.fragment.viewLifeCycle
import com.rasulov.feature.domain.base.Error
import com.rasulov.feature.domain.base.Loading
import com.rasulov.feature.domain.base.Success
import com.rasulov.feature.presentation.base.ErrorTasks
import com.rasulov.feature.presentation.base.Task
import com.rasulov.main.R
import com.rasulov.main.databinding.FragmentAllCategoriesBinding
import com.rasulov.main.presentation.all_categories.setup.setupAdapter
import com.rasulov.main.presentation.all_categories.setup.setupList
import com.rasulov.ui.viewbinding_delegate.viewBindings
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllCategoriesFragment : Fragment(R.layout.fragment_all_categories) {

    internal val binding: FragmentAllCategoriesBinding by viewBindings()

    internal val viewModel: AllCategoriesViewModel by viewModels()

    internal val errorTasks by viewLifeCycle {
        ErrorTasks(viewLifecycleOwner)
    }

    internal val itemsAdapter by viewLifeCycle {
        setupAdapter()
    }

    internal val viewPool = RecyclerView.RecycledViewPool()

    private val jobs: MutableList<Job> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeAllCategories()

    }

    private fun observeAllCategories() =
        viewLifeCycleScope.launch {
            viewModel.allCategoriesFlow.collectLatest {
                when (it) {
                    is Loading -> Unit
                    is Success -> itemsAdapter.submitList(it.value)
                    is Error -> showErrorBarAndAddErrorTask(it.type, getErrorTask())
                }
            }
        }.also { jobs.add(it) }

    private fun getErrorTask(): Task = {
        jobs.cancelAndClear()
        observeAllCategories()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        jobs.cancelAndClear()
    }

    private fun MutableList<Job>.cancelAndClear() {
        this.forEach { it.cancel() }
        this.clear()
    }
}