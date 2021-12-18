package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity

@Entity
data class ShortMovieDbModel(
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    val isFavorite: Boolean,
    val favoriteDate: Long,
    val topPopularPlace: Int = 0,
    val top250Place: Int = 0
)
