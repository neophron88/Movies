package com.rasulov.common

import android.app.Application
import androidx.annotation.StringRes

interface StringResource {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String
}

class StringResourceContext(
    private val application: Application
) : StringResource {


    override fun getString(@StringRes resId: Int): String {
        return application.getString(resId)
    }

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return application.getString(resId, formatArgs)
    }
}