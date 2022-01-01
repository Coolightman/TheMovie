package com.coolightman.themovie.util

import android.content.res.Resources

object ColumnCount {

    private const val IMAGE_WIDTH = 360
    private const val MIN_COLUMN = 2

    fun getColumnCount(resources: Resources): Int {
        val displayWidth = resources.displayMetrics.widthPixels
        val columns = displayWidth / IMAGE_WIDTH
        return if (columns > MIN_COLUMN) columns
        else MIN_COLUMN
    }
}