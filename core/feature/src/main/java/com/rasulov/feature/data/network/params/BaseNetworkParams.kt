package com.rasulov.feature.data.network.params

import androidx.annotation.StringDef


open class BaseNetworkParams(
    val page: Int? = null,
    @Lang val language: String
) {

    companion object {
        const val PAGE_KEY = "page"

        const val LANGUAGE_KEY = "language"
        const val EN = "en-US"
        const val RU = "ru-RU"
    }

    @StringDef(EN, RU)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Lang

}