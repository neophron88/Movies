package com.rasulov.feature.data.network.cache_info

import com.rasulov.feature.utils.CurrentTime


fun Cache.status(time: CurrentTime): Status =
    if (maxAge != null)
        status(maxAge, time)
    else
        status(defaultMaxAgeIfOtherNull, time)


private fun Cache.status(age: Int, time: CurrentTime): Status {
    val isOutdated = time.isOutdated(updatedTimeInSeconds + age)
    return if (isOutdated) Revalidate(eTag) else NotOutdated
}

private fun CurrentTime.isOutdated(timeInSec: Long): Boolean =
    getTimeInSeconds() >= timeInSec

