package com.rasulov.feature.presentation

import androidx.lifecycle.LifecycleOwner
import com.rasulov.library.ktx.onDestroy

typealias Task = () -> Unit


class ErrorTasks(
    lifecycleOwner: LifecycleOwner
) {

    private val tasks: MutableList<Task> = mutableListOf()

    init {
        lifecycleOwner.onDestroy { clear() }
    }


    fun add(task: Task) {
        tasks.add(task)
    }

    fun remove(task: Task) {
        tasks.remove(task)
    }

    fun executeTasks() {
        tasks.forEach { it.invoke() }
        clear()
    }

    private fun clear() = tasks.clear()

}
