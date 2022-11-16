package com.rasulov.ui.ktx.fragment

import android.graphics.drawable.GradientDrawable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun Fragment.getGradientDrawable(@DrawableRes res: Int): GradientDrawable {
    return ContextCompat.getDrawable(requireContext(), res) as GradientDrawable
}

fun Fragment.disableTransitionOverlap() {
    allowEnterTransitionOverlap = false
    allowReturnTransitionOverlap = false
}


