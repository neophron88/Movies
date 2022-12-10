package com.rasulov.feature.di

import android.app.Application
import com.rasulov.feature.utils.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    fun providesCurrentLanguage(): CurrentLanguage = CurrentLanguageImpl()

    @Provides
    fun providesCurrentTime(): CurrentTime = CurrentTimeImpl()

    @Provides
    fun providesStringRes(application: Application): StringResource =
        StringResourceImpl(application)

}