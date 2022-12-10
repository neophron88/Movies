package com.rasulov.main.presentation.all_categories.setup.viewholder

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.rasulov.library.ktx.onDestroy
import com.rasulov.library.rv_adapter_delegate.ItemViewHolder
import kotlinx.coroutines.*

abstract class SafeItemViewHolder<I : Any>(
    view: View,
    viewLifecycleOwner: LifecycleOwner
) : ItemViewHolder<I>(view) {

    val viewHolderScope = MainScope()
    private val jobs = mutableListOf<Job>()

    init {
        viewLifecycleOwner.onDestroy { cancelCoScope() }
    }

    override fun onBind(item: I) {
        cancelJobs()
    }

    override fun unBind() {
        cancelJobs()
    }

    fun CoroutineScope.safeLaunch(
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        val job = launch { block() }
        jobs.add(job)
        return job
    }

    fun cancelJobs() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    private fun cancelCoScope() = viewHolderScope.cancel()

}