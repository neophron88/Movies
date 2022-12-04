package com.rasulov.feature.presentation.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

typealias Task = () -> Unit


class ErrorTasks(
    lifecycleOwner: LifecycleOwner
) {

    private val tasks: MutableList<Task> = mutableListOf()

    init {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                clear()
            }
        })
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

    fun clear() = tasks.clear()

}
