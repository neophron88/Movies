package com.rasulov.database.di

import com.rasulov.database.MoviesDatabase
import com.rasulov.database.all_genres_dao.AllGenresDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class DatabaseDaosModule {

    @Provides
    @Singleton
    fun provideAllGenresDao(database: MoviesDatabase): AllGenresDao =
        database.getAllCategoriesDao()
}