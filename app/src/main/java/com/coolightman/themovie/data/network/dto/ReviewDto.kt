package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("reviewId") val reviewId: Long,
    @SerializedName("reviewType") var reviewType: String? = null,
    @SerializedName("reviewData") var reviewData: String? = null,
    @SerializedName("userPositiveRating") var userPositiveRating: Int? = null,
    @SerializedName("userNegativeRating") var userNegativeRating: Int? = null,
    @SerializedName("reviewAutor") var reviewAutor: String? = null,
    @SerializedName("reviewTitle") var reviewTitle: String? = null,
    @SerializedName("reviewDescription") var reviewDescription: String? = null
)