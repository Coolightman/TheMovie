package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class MovieSearchDto(
    @SerializedName("filmId") var movieId: Int? = null,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameEn") var nameEn: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("year") var year: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("filmLength") var filmLength: String? = null,
    @SerializedName("countries") var countries: List<CountryDto> = arrayListOf(),
    @SerializedName("genres") var genres: List<GenreDto> = arrayListOf(),
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("ratingVoteCount") var ratingVoteCount: Int? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("posterUrlPreview") var posterUrlPreview: String? = null
)
