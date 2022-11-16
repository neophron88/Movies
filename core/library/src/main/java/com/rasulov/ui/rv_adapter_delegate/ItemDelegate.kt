package com.rasulov.ui.rv_adapter_delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlin.reflect.KClass

abstract class ItemDelegate<I : Any>(
    val itemClass: KClass<I>
) {

    abstract fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<I>

    abstract fun areItemsTheSame(oldItem: I, newItem: I): Boolean

    abstract fun areContentsTheSame(oldItem: I, newItem: I): Boolean

    open fun getChangePayload(oldItem: I, newItem: I): Any? = null

}