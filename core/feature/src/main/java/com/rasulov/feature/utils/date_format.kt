package com.rasulov.feature.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.changeDateFormat(
    oldFormat: String,
    newFormat: String,
): String {
    val oldDateString = this
    val sdf = SimpleDateFormat(oldFormat, Locale.ENGLISH)
    val d = sdf.parse(oldDateString)!!
    sdf.applyPattern(newFormat)
    return sdf.format(d)
}