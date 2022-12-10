package com.rasulov.main.di.data

import com.rasulov.main.data.OfflineFirstAllCategoriesRepository
import com.rasulov.main.data.sources.local.AllCategoriesLocalDataSource
import com.rasulov.main.data.sources.network.AllCategoriesNetworkDataSource
import com.rasulov.main.di.MainFeatureScope
import com.rasulov.main.domain.repository.AllCategoriesRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope

@Module(includes = [AllCategoriesRepoDepsModule::class])
class DataModule {

    @Provides
    @MainFeatureScope
    fun provideAllCategoriesRepository(
        local: AllCategoriesLocalDataSource,
        network: AllCategoriesNetworkDataSource,
        longLiveCoroutineScope: CoroutineScope
    ): AllCategoriesRepository =
        OfflineFirstAllCategoriesRepository(local, network, longLiveCoroutineScope)


}