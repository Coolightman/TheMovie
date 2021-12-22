package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShortMovieDbModel(
    @PrimaryKey
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    var isFavorite: Boolean = false,
    var topPopularPlace: Int = 0,
    var top250Place: Int = 0
)
