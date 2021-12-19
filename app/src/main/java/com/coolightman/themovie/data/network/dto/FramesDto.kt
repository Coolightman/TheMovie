package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class FramesDto(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("totalPages") var totalPages: Int? = null,
    @SerializedName("items") var items: List<FrameDto> = listOf()
)
