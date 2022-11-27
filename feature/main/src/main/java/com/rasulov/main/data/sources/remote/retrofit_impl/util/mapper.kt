package com.rasulov.main.data.sources.remote.retrofit_impl.util

import com.rasulov.main.data.sources.cache_status.CacheInfo
import com.rasulov.main.data.sources.cache_status.sharedpreferences_impl.AllGenresCache
import com.rasulov.main.data.sources.remote.retrofit_impl.service.models.BaseParams
import com.rasulov.shared.util.CurrentTime


fun CacheHeader.toAllGenresCache(params: BaseParams, currentTime: CurrentTime) =
    AllGenresCache(
        language = params.language,
        maxAge = maxAge,
        eTag = eTag,
        defaultMaxAgeIfOtherNull = CacheInfo.DEFAULT_MAX_AGE,
        updatedTimeInSeconds = currentTime.getTimeInSeconds()
    )
