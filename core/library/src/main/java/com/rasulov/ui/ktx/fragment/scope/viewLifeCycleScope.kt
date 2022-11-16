package com.rasulov.ui.ktx.fragment.scope

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


inline val Fragment.viewLifeCycleScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope

suspend fun Fragment.repeatOnViewLifeCycle(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.repeatOnLifecycle(state, block)
}

fun Fragment.repeatWhenViewStarted(block: suspend CoroutineScope.() -> Unit) {
    viewLifeCycleScope.launch { repeatOnViewLifeCycle(Lifecycle.State.STARTED, block) }
}