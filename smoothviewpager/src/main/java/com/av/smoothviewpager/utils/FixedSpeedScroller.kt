package com.av.smoothviewpager.utils

import android.content.Context
import android.widget.Scroller

/**
 * Created by Astrit Veliu on 09,September,2019
 * updated on 06, October, 2021
 */
class FixedSpeedScroller@JvmOverloads constructor(context: Context) : Scroller(context) {

    private val mDuration = 1500

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }
}