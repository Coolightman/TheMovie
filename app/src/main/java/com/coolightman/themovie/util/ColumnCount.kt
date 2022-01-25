package com.coolightman.themovie.util

import android.content.res.Resources

object ColumnCount {

    private const val IMAGE_WIDTH = 360
    private const val MIN_COLUMN = 2
    private const val FIRST_LIST_ITEM = 0

    fun getColumnCount(resources: Resources): Int {
        val displayWidth = resources.displayMetrics.widthPixels
        val columns = displayWidth / IMAGE_WIDTH
        return if (columns > MIN_COLUMN) columns
        else MIN_COLUMN
    }

    fun getRandomNumber(listSize: Int): Int {
        return if (listSize > 1) {
            val max = listSize - 1
            val min = 0
            (Math.random() * (max - min + 1) + min).toInt()
        } else {
            FIRST_LIST_ITEM
        }
    }
}