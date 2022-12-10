package com.rasulov.main.di.ui

import androidx.lifecycle.ViewModel
import com.rasulov.feature.utils.CurrentLanguage
import com.rasulov.feature.utils.StringResource
import com.rasulov.main.domain.repository.AllCategoriesRepository
import com.rasulov.main.presentation.all_categories.AllCategoriesViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {


    @IntoMap
    @ViewModelKey(AllCategoriesViewModel::class)
    @Provides
    fun bindAllCategoriesViewModel(
        stringRes: StringResource,
        language: CurrentLanguage,
        repository: AllCategoriesRepository
    ): ViewModel =
        AllCategoriesViewModel(stringRes, language, repository)


}