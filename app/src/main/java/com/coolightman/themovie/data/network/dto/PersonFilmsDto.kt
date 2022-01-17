package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class PersonFilmsDto(
    @SerializedName("filmId") val movieId: Long,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("professionKey") var professionKey: String? = null
)
