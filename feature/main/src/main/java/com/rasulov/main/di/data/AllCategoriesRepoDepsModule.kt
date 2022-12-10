package com.rasulov.main.di.data

import android.app.Application
import com.rasulov.database.all_genres_dao.AllGenresDao
import com.rasulov.feature.data.network.cache_info.CacheInfo
import com.rasulov.feature.data.network.family_genre.FamilyGenreInterceptor
import com.rasulov.feature.utils.CurrentTime
import com.rasulov.main.data.sources.local.AllCategoriesLocalDataSource
import com.rasulov.main.data.sources.local.room_impl.AllCategoriesRoomDataSource
import com.rasulov.main.data.sources.network.AllCategoriesNetworkDataSource
import com.rasulov.main.data.sources.network.cache_info.sharedpreferences_impl.*
import com.rasulov.main.data.sources.network.retrofit_impl.AllCategoriesRetrofitDataSource
import com.rasulov.main.di.MainFeatureScope
import com.rasulov.network.all_genres_service.AllGenresService
import dagger.Module
import dagger.Provides


@Module
internal class AllCategoriesRepoDepsModule {

    @Provides
    @MainFeatureScope
    fun provideGenreCacheInfo(
        application: Application,
        currentTime: CurrentTime
    ): CacheInfo<GenreValue, GenreCache> =
        GenreCacheInfoSharedPref(application, currentTime)


    @Provides
    @MainFeatureScope
    fun provideAllGenresCacheInfo(
        application: Application,
        currentTime: CurrentTime
    ): CacheInfo<AllGenresValue, AllGenresCache> =
        AllGenresCacheInfoSharedPref(application, currentTime)


    @Provides
    @MainFeatureScope
    fun provideAllCategoriesNetworkDataSource(
        allGenresService: AllGenresService,
        currentTime: CurrentTime,
        familyGenreInterceptor: FamilyGenreInterceptor,
        allGenresCache: CacheInfo<AllGenresValue, AllGenresCache>,
        genreCache: CacheInfo<GenreValue, GenreCache>
    ): AllCategoriesNetworkDataSource =
        AllCategoriesRetrofitDataSource(
            service = allGenresService,
            currentTime = currentTime,
            genreInterceptor = familyGenreInterceptor,
            allGenresCacheInfo = allGenresCache,
            genreCacheInfo = genreCache
        )


    @Provides
    @MainFeatureScope
    fun provideAllCategoriesLocalDataSource(
        dao: AllGenresDao
    ): AllCategoriesLocalDataSource =
        AllCategoriesRoomDataSource(dao)
}