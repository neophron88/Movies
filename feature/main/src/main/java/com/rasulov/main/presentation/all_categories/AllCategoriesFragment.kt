package com.rasulov.main.presentation.all_categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.rasulov.feature.domain.Error
import com.rasulov.feature.domain.Loading
import com.rasulov.feature.domain.Success
import com.rasulov.feature.presentation.ErrorTasks
import com.rasulov.feature.presentation.Task
import com.rasulov.feature.presentation.viewModelFactory.viewModelsProvider
import com.rasulov.library.ktx.fragment.scope.viewLifeCycleScope
import com.rasulov.library.ktx.fragment.viewLifeCycle
import com.rasulov.library.viewbinding_delegate.viewBindings
import com.rasulov.main.R
import com.rasulov.main.databinding.FragmentAllCategoriesBinding
import com.rasulov.main.presentation.all_categories.setup.setupAdapter
import com.rasulov.main.presentation.all_categories.setup.setupList
import com.rasulov.main.presentation.all_categories.setup.showErrorBarAndAddErrorTask
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


internal class AllCategoriesFragment : Fragment(R.layout.fragment_all_categories) {

    private val jobs: MutableList<Job> = mutableListOf()

    internal val binding: FragmentAllCategoriesBinding by viewBindings()

    internal val viewModel: AllCategoriesViewModel by viewModelsProvider()

    internal val errorTasks by viewLifeCycle {
        ErrorTasks(viewLifecycleOwner)
    }

    internal val itemsAdapter by viewLifeCycle {
        setupAdapter()
    }

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