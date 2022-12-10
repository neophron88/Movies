package com.rasulov.library.ktx.fragment

import android.graphics.drawable.GradientDrawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Fragment.getGradientDrawable(@DrawableRes res: Int): GradientDrawable {
    return ContextCompat.getDrawable(requireContext(), res) as GradientDrawable
}

fun Fragment.disableTransitionOverlap() {
    allowEnterTransitionOverlap = false
    allowReturnTransitionOverlap = false
}


