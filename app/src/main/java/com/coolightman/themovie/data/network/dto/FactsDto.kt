package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class FactsDto(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("items") var items: List<FactDto> = listOf()
)
