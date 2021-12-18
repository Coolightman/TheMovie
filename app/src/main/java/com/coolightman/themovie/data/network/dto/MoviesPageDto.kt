package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class MoviesPageDto(
    @SerializedName("pagesCount") var pagesCount: Int? = null,
    @SerializedName("films") var movies: List<ShortMovieDto> = emptyList()
)
