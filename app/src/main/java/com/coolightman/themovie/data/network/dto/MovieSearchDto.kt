package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class MovieSearchDto(
    @SerializedName("filmId") var movieId: Long,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("year") var year: String? = null,
    @SerializedName("filmLength") var filmLength: String? = null,
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("posterUrlPreview") var posterUrlPreview: String? = null
)
