package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class FrameDto(
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("previewUrl") var previewUrl: String? = null
)
