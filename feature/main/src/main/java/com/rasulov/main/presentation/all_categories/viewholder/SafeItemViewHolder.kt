package com.rasulov.main.presentation.all_categories.viewholder

import android.view.View
import com.rasulov.ui.rv_adapter_delegate.ItemViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

abstract class SafeItemViewHolder<I : Any>(
    view: View
) : ItemViewHolder<I>(view) {

    val viewHolderScope = MainScope()
    private val jobs = mutableListOf<Job>()


    fun CoroutineScope.safeLaunch(
        block: suspend CoroutineScope.() -> Unit
    ) {
        val job = launch { block() }
        jobs.add(job)
    }


    override fun unBind() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

}