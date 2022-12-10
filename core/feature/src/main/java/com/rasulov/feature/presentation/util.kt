package com.rasulov.feature.presentation

import android.content.Context
import com.rasulov.feature.R
import com.rasulov.feature.domain.*
import com.rasulov.feature.utils.CurrentLanguage


fun CurrentLanguage.asLanguage(): Language =
    if (get() == "ru") Language.RU
    else Language.EN


fun ErrorType.toString(context: Context) =
    when (this) {
        is NoConnection -> context.getString(R.string.no_connection)
        is BackendFault -> context.getString(R.string.backend_fault)
        is Unknown -> context.getString(R.string.unknown_conn_error)
        else -> error("$this is unhandled ")
    }


