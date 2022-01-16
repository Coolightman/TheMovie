package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class ReviewsDto(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("filmId") val movieId: Long,
    @SerializedName("reviewAllCount") var reviewAllCount: Int? = null,
    @SerializedName("reviewAllPositiveRatio") var reviewAllPositiveRatio: String? = null,
    @SerializedName("reviewPositiveCount") var reviewPositiveCount: String? = null,
    @SerializedName("reviewNegativeCount") var reviewNegativeCount: String? = null,
    @SerializedName("reviewNeutralCount") var reviewNeutralCount: String? = null,
    @SerializedName("pagesCount") var pagesCount: Int? = null,
    @SerializedName("reviews") var reviews: List<ReviewDto> = arrayListOf()
)