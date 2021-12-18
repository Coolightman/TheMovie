package com.coolightman.themovie.domain.entity

data class MovieShort(
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    var isFavorite: Boolean
)
