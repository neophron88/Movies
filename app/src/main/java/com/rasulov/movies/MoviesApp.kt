package com.rasulov.movies

import android.app.Application
import com.rasulov.feature.di.DependencyProvider
import com.rasulov.movies.di.DaggerMoviesAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MoviesApp : Application(), DependencyProvider {

    private val applicationScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override val dependency by lazy {
        DaggerMoviesAppComponent
            .factory()
            .create(applicationScope, this)
    }

}