package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class ShortMovieDto(
    @SerializedName("filmId") val movieId: Long,
    @SerializedName("rating") val rating: String? = null,
    @SerializedName("posterUrlPreview") val posterPreview: String? = null
)
