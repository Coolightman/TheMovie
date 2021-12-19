package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("url") var url: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("site") var site: String? = null
)