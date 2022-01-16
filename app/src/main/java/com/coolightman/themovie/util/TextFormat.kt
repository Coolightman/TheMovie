package com.coolightman.themovie.util

object TextFormat {

    fun formatRatingPercent(rating: String?): String? {
        rating?.let {
            return if (rating.contains("%") && rating.contains(".")) {
                "${rating.split(".")[0]}%"
            } else {
                rating
            }
        }
        return null
    }

    fun formatRatingAwaitPercent(ratingAwait: String?): String? {
        ratingAwait?.let {
            return if (ratingAwait.contains(".")) {
                "${ratingAwait.split(".")[0]}%"
            } else {
                ratingAwait
            }
        }
        return null
    }

    fun cutTextSize(text: String, number: Int): String {
        return if (text.length > number){
            "${text.substring(0, number).trim()}.. "
        } else{
            text
        }
    }
}