package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class CountryDto (
    @SerializedName("country") var country: String? = null
)