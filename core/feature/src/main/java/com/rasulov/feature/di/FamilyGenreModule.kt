package com.rasulov.feature.di

import android.app.Application
import com.rasulov.feature.data.network.family_genre.FamilyGenreInterceptor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FamilyGenreModule {


    @Provides
    @Singleton
    fun provideFamilyGenre(application: Application): FamilyGenreInterceptor =
        FamilyGenreInterceptor(application)

}