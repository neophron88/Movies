package com.rasulov.database.di

import android.app.Application
import com.rasulov.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application):MoviesDatabase =
        MoviesDatabase.getDatabase(application)

}