package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("genre") var genre: String? = null
)
