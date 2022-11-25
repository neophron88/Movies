package com.rasulov.feature.util

interface CurrentTime {

    fun getTimeInMillis(): Long
}

class CurrentTimeImpl : CurrentTime {

    override fun getTimeInMillis(): Long =
        System.currentTimeMillis()


}