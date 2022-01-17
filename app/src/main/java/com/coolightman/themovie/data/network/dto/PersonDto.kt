package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class PersonDto(
    @SerializedName("personId") val personId: Long,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("sex") var sex: String? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("growth") var growth: Int? = null,
    @SerializedName("birthday") var birthday: String? = null,
    @SerializedName("death") var death: String? = null,
    @SerializedName("age") var age: Int? = null,
    @SerializedName("birthplace") var birthplace: String? = null,
    @SerializedName("deathplace") var deathplace: String? = null,
    @SerializedName("profession") var profession: String? = null,
    @SerializedName("facts") var facts: List<String> = arrayListOf(),
    @SerializedName("films") var films: List<PersonFilmsDto> = arrayListOf()
)