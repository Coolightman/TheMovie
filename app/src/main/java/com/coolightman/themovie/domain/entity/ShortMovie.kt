package com.coolightman.themovie.domain.entity

data class ShortMovie(
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    val isFavorite: Boolean
)
