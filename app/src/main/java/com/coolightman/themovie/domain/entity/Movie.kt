package com.coolightman.themovie.domain.entity

data class Movie(
    val movieId: Long,
    val titleOriginal: String?,
    val titleRu: String?,
    var slogan: String?,
    val rating: String?,
    val ratingCount: String?,
    val posterPreview: String?,
    val poster: String?,
    var isFavorite: Boolean,
    var favoriteDate: Long?,
    val releaseDate: String?,
    val duration: String?,
    val description: String?,
    val genres: List<Genre>,
    val countries: List<Country>,
    val webUrl: String?,
    val topPopularPlace: Int,
    val top250Place: Int
)
