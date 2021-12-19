package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class FactDto(
    @SerializedName("text") var text: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("spoiler") var spoiler: Boolean? = null
)
