package com.rasulov.main.di

import android.app.Application
import com.rasulov.database.all_genres_dao.AllGenresDao
import com.rasulov.feature.data.network.family_genre.FamilyGenreInterceptor
import com.rasulov.feature.di.UtilsModule
import com.rasulov.feature.presentation.viewModelFactory.ViewModelFactory
import com.rasulov.main.di.data.DataModule
import com.rasulov.main.di.ui.ViewModelModule
import com.rasulov.network.all_genres_service.AllGenresService
import dagger.Component
import kotlinx.coroutines.CoroutineScope


@MainFeatureScope
@Component(
    modules = [
        DataModule::class,
        UtilsModule::class,
        ViewModelModule::class
    ],
    dependencies = [MainFeatureDeps::class]
)
internal interface MainFeatureComponent {

    val viewModelFactory: ViewModelFactory

    @Component.Builder
    interface Builder {

        fun deps(deps: MainFeatureDeps): Builder

        fun build(): MainFeatureComponent
    }

}


interface MainFeatureDeps {

    fun getAllGenresService(): AllGenresService

    fun getAllGenresDao(): AllGenresDao

    fun getFamilyGenreInterceptor(): FamilyGenreInterceptor

    fun getLongLiveScope(): CoroutineScope

    fun getApplication(): Application
}