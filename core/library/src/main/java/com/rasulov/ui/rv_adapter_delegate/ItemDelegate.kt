package com.rasulov.ui.rv_adapter_delegate

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KClass

typealias ItemViewHolderProducer<I> = (view: View) -> ItemViewHolder<I>

class ItemDelegate<I : Any>(
    val itemClass: KClass<I>,
    @LayoutRes val layout: Int,
    val diffUtil: DiffUtil.ItemCallback<I>,
    val itemViewHolderProducer: ItemViewHolderProducer<I>
)