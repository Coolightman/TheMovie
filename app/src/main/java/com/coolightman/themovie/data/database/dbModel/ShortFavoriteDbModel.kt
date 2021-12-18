package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShortFavoriteDbModel(
    @PrimaryKey
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    val favoriteDate: Long
)
