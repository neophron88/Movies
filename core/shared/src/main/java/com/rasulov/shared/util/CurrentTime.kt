package com.rasulov.shared.util

interface CurrentTime {

    fun getTimeInMillis(): Long

    fun getTimeInSeconds(): Long
}

class CurrentTimeImpl : CurrentTime {

    override fun getTimeInMillis(): Long =
        System.currentTimeMillis()

    override fun getTimeInSeconds(): Long =
        System.currentTimeMillis() * 1000L


}