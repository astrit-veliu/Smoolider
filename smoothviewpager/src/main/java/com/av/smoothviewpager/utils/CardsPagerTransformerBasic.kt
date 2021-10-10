package com.av.smoothviewpager.utils

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/**
 * Created by Astrit Veliu on 09,September,2019
 * updated on 06, October, 2021
 */
class CardsPagerTransformerBasic(
    private val baseElevation: Int,
    private val raisingElevation: Int,
    private val smallerScale: Float
) : PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val absPosition = abs(position)
        if (absPosition >= 1) {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) page.elevation = baseElevation.toFloat()
            page.scaleY = smallerScale
        } else {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) page.elevation = (1 - absPosition) * raisingElevation + baseElevation
            page.scaleY = (smallerScale - 1) * absPosition + 1
        }
    }
}