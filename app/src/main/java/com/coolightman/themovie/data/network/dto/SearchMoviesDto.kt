package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class SearchMoviesDto(
    @SerializedName("keyword") var keyword: String? = null,
    @SerializedName("pagesCount") var pagesCount: Int? = null,
    @SerializedName("films") var movies: ArrayList<MovieSearchDto> = arrayListOf(),
    @SerializedName("searchFilmsCountResult") var searchFilmsCountResult: Int? = null
)
