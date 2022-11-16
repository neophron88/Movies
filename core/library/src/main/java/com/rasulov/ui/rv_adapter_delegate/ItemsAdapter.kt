package com.rasulov.ui.rv_adapter_delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


open class ItemsAdapter<I : Any>(
    private val mediator: MediatorItemDelegate<I>
) : ListAdapter<I, ItemViewHolder<I>>(mediator.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<I> {
        return mediator.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<I>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder<I>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) holder.bind(getItem(position), payloads)
        else super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return mediator.getItemViewType(getItem(position))
    }

    override fun onViewRecycled(holder: ItemViewHolder<I>) {
        holder.unBind()
    }

}

