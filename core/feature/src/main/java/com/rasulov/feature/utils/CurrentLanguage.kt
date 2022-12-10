package com.rasulov.feature.utils

import java.util.*

interface CurrentLanguage {

    fun get():String
}

internal class CurrentLanguageImpl : CurrentLanguage {

    override fun get(): String = Locale.getDefault().language
}