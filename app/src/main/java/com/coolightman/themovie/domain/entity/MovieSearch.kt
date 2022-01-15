package com.coolightman.themovie.domain.entity

data class MovieSearch(
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    val nameEn: String?,
    val nameRu: String?,
    val releaseDate: String?,
    val duration: String?
)
