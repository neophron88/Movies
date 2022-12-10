package com.rasulov.main.presentation

import androidx.fragment.app.Fragment
import com.rasulov.feature.presentation.viewModelFactory.HasViewModelFactory
import com.rasulov.feature.presentation.viewModelFactory.ViewModelFactory
import com.rasulov.feature.utils.extractDependency
import com.rasulov.main.R
import com.rasulov.main.di.DaggerMainFeatureComponent
import com.rasulov.main.di.MainFeatureComponent

class MainHostFragment : Fragment(R.layout.fragment_main_host), HasViewModelFactory {

    private val mainFeatureComponent: MainFeatureComponent by lazy {
        DaggerMainFeatureComponent.builder()
            .deps(extractDependency())
            .build()
    }

    override val viewModelFactory: ViewModelFactory by lazy {
        mainFeatureComponent.viewModelFactory
    }

}