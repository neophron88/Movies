package com.rasulov.library.single_use_data

class Handler<T>(private var value: T?) {

    fun getVal(): T? {
        return value?.also { value = null }
    }
}