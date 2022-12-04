package com.rasulov.main.data.network_datasource.cache_info.sharedpreferences_impl

import com.rasulov.main.data.network_datasource.cache_info.Cache
import com.rasulov.common.CurrentTime
import com.rasulov.main.data.network_datasource.cache_info.NotOutdated
import com.rasulov.main.data.network_datasource.cache_info.Revalidate
import com.rasulov.main.data.network_datasource.cache_info.Status

fun Cache.status(time: CurrentTime): Status {

    if (maxAge != null) {
        val isOutdated = time.isOutdated(updatedTimeInSeconds + maxAge)
        return if (isOutdated) Revalidate(eTag) else NotOutdated
    }

    val isOutdated = time.isOutdated(updatedTimeInSeconds + defaultMaxAgeIfOtherNull)
    return if (isOutdated) Revalidate() else NotOutdated

}

private fun CurrentTime.isOutdated(timeInSec: Long): Boolean =
    getTimeInSeconds() >= timeInSec

