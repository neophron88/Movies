package com.rasulov.feature.presentation.shared

import android.content.Context
import com.rasulov.common.CurrentLanguage
import com.rasulov.feature.R
import com.rasulov.feature.domain.base.BackendFault
import com.rasulov.feature.domain.base.ErrorType
import com.rasulov.feature.domain.base.NoConnection
import com.rasulov.feature.domain.base.Unknown
import com.rasulov.feature.domain.base.enums.Language


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


