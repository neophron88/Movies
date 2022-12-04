package com.rasulov.common

import java.util.*

class CurrentLanguage {

    fun get(): String = Locale.getDefault().language
}