package com.rasulov.library.ktx

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

inline fun LifecycleOwner.onDestroy(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) { block() }
    })
}