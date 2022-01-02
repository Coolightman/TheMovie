package com.coolightman.themovie.util

object TextFormat {

    fun formatPercent(rating: String?): String? {
        rating?.let {
            return if (rating.contains("%") && rating.contains(".")) {
                "${rating.split(".")[0]}%"
            } else {
                rating
            }
        }
        return null
    }
}