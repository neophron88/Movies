package com.rasulov.main.presentation.all_categories

import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.rasulov.feature.R
import com.rasulov.feature.domain.base.ErrorType
import com.rasulov.feature.presentation.base.Task
import com.rasulov.feature.presentation.shared.toString

fun AllCategoriesFragment.showErrorBarAndAddErrorTask(
    type: ErrorType,
    task: Task
) {
    Snackbar
        .make(requireView(), type.toString(requireContext()), Snackbar.LENGTH_LONG)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .setAction(requireContext().getString(R.string.refresh)) { errorTasks.executeTasks() }
        .show()

    errorTasks.add(task)
}