package com.rasulov.shared.data.remote.models

import com.rasulov.shared.data.remote.models.RemoteMovie
import com.squareup.moshi.Json

class RemoteMovieList(
    @Json(name = "results")
    val movies: List<RemoteMovie>
)