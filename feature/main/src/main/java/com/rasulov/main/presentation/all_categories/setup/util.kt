package com.rasulov.main.presentation.all_categories.setup

import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.rasulov.feature.R
import com.rasulov.feature.domain.ErrorType
import com.rasulov.feature.presentation.Task
import com.rasulov.feature.presentation.toString
import com.rasulov.main.presentation.all_categories.AllCategoriesFragment


internal fun AllCategoriesFragment.showErrorBarAndAddErrorTask(
    type: ErrorType,
    task: Task
) {
    Snackbar
        .make(requireView(), type.toString(requireContext()), Snackbar.LENGTH_INDEFINITE)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .setAction(requireContext().getString(R.string.refresh)) { errorTasks.executeTasks() }
        .show()

    errorTasks.add(task)
}