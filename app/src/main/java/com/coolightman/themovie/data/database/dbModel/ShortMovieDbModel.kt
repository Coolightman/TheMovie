package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity

@Entity
data class ShortMovieDbModel(
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    var isFavorite: Boolean,
    val topPopularPlace: Int,
    val top250Place: Int
)
