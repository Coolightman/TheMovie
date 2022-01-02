package com.coolightman.themovie.util

import com.coolightman.themovie.R
import com.google.android.material.textview.MaterialTextView

object RatingColor {

    fun setRatingColor(view: MaterialTextView, rating: String){
        when {
            rating.contains(Regex("^[7-9]")) -> view.setBackgroundResource(R.drawable.rounded_corner_green)
            rating.contains(Regex("^[56]")) -> view.setBackgroundResource(R.drawable.rounded_corner_grey)
            rating.contains(Regex("^[2-4]")) -> view.setBackgroundResource(R.drawable.rounded_corner_red)
        }
    }
}