package com.rasulov.feature.presentation.viewModelFactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

inline fun <reified VM : ViewModel> Fragment.viewModelsProvider(): Lazy<VM> =
    viewModels(
        factoryProducer = { getViewModelFactoryFromParent() }
    )


fun Fragment.getViewModelFactoryFromParent(): ViewModelFactory {
    var hasViewModelFactory: HasViewModelFactory? = null

    var parent: Fragment? = parentFragment
    while (true) {
        if (parent == null) break
        else if (parent is HasViewModelFactory) {
            hasViewModelFactory = parent
            break
        }
        parent = parent.parentFragment

    }

    return hasViewModelFactory?.viewModelFactory
        ?: error("It is required a parentFragment which implements HasViewModelFactory")
}