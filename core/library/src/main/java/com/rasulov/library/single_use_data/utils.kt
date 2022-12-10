@file:Suppress("unused")

package com.rasulov.library.single_use_data


typealias Observer<T> = (T) -> Unit

fun <T : Any> MutableSingleUseData<T>.toSingleUseData(): SingleUseData<T> = this

