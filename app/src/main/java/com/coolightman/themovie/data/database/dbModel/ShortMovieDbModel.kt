package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShortMovieDbModel(
    @PrimaryKey
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    val isFavorite: Boolean,
    val favoriteDate: Long,
    val topPopularPlace: Int = 0,
    val top250Place: Int = 0
)
