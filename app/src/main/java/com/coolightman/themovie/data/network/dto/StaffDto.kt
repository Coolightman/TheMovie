package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class StaffDto(
    @SerializedName("staffId") val staffId: Long,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("professionText") var professionText: String? = null,
    @SerializedName("professionKey") var professionKey: String? = null
)
