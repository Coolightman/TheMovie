package com.coolightman.themovie.data.network.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("kinopoiskId") val movieId: Long,
    @SerializedName("imdbId") var imdbId: String? = null,
    @SerializedName("nameRu") var nameRu: String? = null,
    @SerializedName("nameOriginal") var nameOriginal: String? = null,
    @SerializedName("posterUrl") var posterUrl: String? = null,
    @SerializedName("posterUrlPreview") var posterUrlPreview: String? = null,
    @SerializedName("reviewsCount") var reviewsCount: Int? = null,
    @SerializedName("ratingGoodReview") var ratingGoodReview: String? = null,
    @SerializedName("ratingGoodReviewVoteCount") var ratingGoodReviewVoteCount: Int? = null,
    @SerializedName("ratingKinopoisk") var ratingKinopoisk: String? = null,
    @SerializedName("ratingKinopoiskVoteCount") var ratingKinopoiskVoteCount: Int? = null,
    @SerializedName("ratingImdb") var ratingImdb: String? = null,
    @SerializedName("ratingImdbVoteCount") var ratingImdbVoteCount: Int? = null,
    @SerializedName("ratingFilmCritics") var ratingFilmCritics: String? = null,
    @SerializedName("ratingFilmCriticsVoteCount") var ratingFilmCriticsVoteCount: Int? = null,
    @SerializedName("ratingAwait") var ratingAwait: String? = null,
    @SerializedName("ratingAwaitCount") var ratingAwaitCount: Int? = null,
    @SerializedName("webUrl") var webUrl: String? = null,
    @SerializedName("year") var year: Int? = null,
    @SerializedName("filmLength") var filmLength: Int? = null,
    @SerializedName("slogan") var slogan: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("ratingAgeLimits") var ratingAgeLimits: String? = null,
    @SerializedName("countries") var countries: List<CountryDto> = arrayListOf(),
    @SerializedName("genres") var genres: List<GenreDto> = arrayListOf()
)
