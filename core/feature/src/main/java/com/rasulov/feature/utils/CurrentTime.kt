package com.rasulov.feature.utils

interface CurrentTime {

    fun getTimeInMillis(): Long

    fun getTimeInSeconds(): Long
}

internal class CurrentTimeImpl : CurrentTime {

    override fun getTimeInMillis(): Long =
        System.currentTimeMillis()

    override fun getTimeInSeconds(): Long =
        getTimeInMillis() / 1000L


}