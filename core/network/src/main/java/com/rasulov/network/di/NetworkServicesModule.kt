package com.rasulov.network.di

import com.rasulov.network.all_genres_service.AllGenresService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
class NetworkServicesModule {

    @Provides
    @Singleton
    fun provideAllCategoriesService(
        retrofit: Retrofit
    ): AllGenresService = retrofit.create(AllGenresService::class.java)
}