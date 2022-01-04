package com.coolightman.themovie.domain.entity

data class Movie(
    val movieId: Long,
    val nameOriginal: String?,
    val nameRu: String?,
    val slogan: String?,
    val rating: String?,
    val ratingCount: String?,
    val ratingAwait: String?,
    val ratingAwaitCount: String?,
    val ratingImdb: String?,
    val ratingImdbCount: String?,
    val ratingCritics: String?,
    val ratingCriticsCount: String?,
    val posterPreview: String?,
    val poster: String?,
    val isFavorite: Boolean,
    val releaseDate: String?,
    val duration: String?,
    val description: String?,
    val genres: List<Genre>,
    val countries: List<Country>,
    val webUrl: String?,
    val ageLimit: String?
)
