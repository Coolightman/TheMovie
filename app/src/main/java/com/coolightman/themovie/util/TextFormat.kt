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

    fun convertGrowth(growth: Int?): String? {
        return if (growth != null && growth > 0) {
            val meters = growth / 100
            val centimeters = growth % 100
            "$meters.$centimeters м"
        } else null
    }

    fun convertData(date: String?): String? {
        return if (date != null) {
            val split = date.split("-")
            "${split[2]}-${split[1]}-${split[0]}"
        } else null
    }
}