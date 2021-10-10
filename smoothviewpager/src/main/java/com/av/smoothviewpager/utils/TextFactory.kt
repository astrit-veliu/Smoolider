package com.av.smoothviewpager.utils

import android.content.Context
import androidx.annotation.StyleRes
import android.widget.TextView
import android.view.Gravity
import android.view.View
import android.widget.ViewSwitcher.ViewFactory
import androidx.core.widget.TextViewCompat

/**
 * Created by Astrit Veliu on 09,September,2019
 * updated on 06, October, 2021
 */
class TextFactory(@StyleRes val styleId: Int, private val center: Boolean, private val mContext: Context) : ViewFactory {

    override fun makeView(): View {
        val textView = TextView(mContext)
        textView.apply {
            if (center) gravity = Gravity.CENTER
            TextViewCompat.setTextAppearance(this, styleId)
        }.also { return it }
    }
}