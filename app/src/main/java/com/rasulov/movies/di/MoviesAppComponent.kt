package com.rasulov.movies.di

import android.app.Application
import com.rasulov.database.di.DatabaseDaosModule
import com.rasulov.feature.di.FamilyGenreModule
import com.rasulov.main.di.MainFeatureDeps
import com.rasulov.network.di.NetworkServicesModule
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkServicesModule::class,
        DatabaseDaosModule::class,
        FamilyGenreModule::class
    ]
)
interface MoviesAppComponent : MainFeatureDeps {


    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance coroutineScope: CoroutineScope,
            @BindsInstance application: Application
        ): MoviesAppComponent
    }

}