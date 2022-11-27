package com.rasulov.main.data.sources.cache_status.sharedpreferences_impl

import com.rasulov.main.data.sources.cache_status.Cache
import com.rasulov.shared.util.CurrentTime
import com.rasulov.main.data.sources.cache_status.NotOutdated
import com.rasulov.main.data.sources.cache_status.Revalidate
import com.rasulov.main.data.sources.cache_status.Status

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

