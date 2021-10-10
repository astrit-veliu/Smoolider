package com.av.smoothviewpager.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import android.content.Intent
import android.graphics.BitmapFactory.Options
import android.net.Uri
import kotlin.math.roundToInt

/**
 * Created by Astrit Veliu on 09,September,2019
 * updated on 06, October, 2021
 */

fun Context.openWebPage(url: String) {
    if (url.equals("", ignoreCase = true) || url.isEmpty()) {
        Toast.makeText(this, "Bad url", Toast.LENGTH_SHORT).show()
    } else {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(browserIntent)
    }
}

fun Context.decodeSampledBitmapFromResource(resourceId: Int, width: Int, height: Int): Bitmap {
    val options = Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, resourceId, options)
    options.inSampleSize = calculateInSampleSize(options, width, height)
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(resources, resourceId, options)
}

private fun calculateInSampleSize(options: Options, reqWidth: Int, reqHeight: Int): Int {
    val stretchWidth = (options.outWidth.toFloat() / reqWidth.toFloat()).roundToInt()
    val stretchHeight = (options.outHeight.toFloat() / reqHeight.toFloat()).roundToInt()
    return if (stretchWidth <= stretchHeight) stretchHeight else stretchWidth
}