package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity

@Entity
data class ShortFavoriteDbModel(
    val movieId: Long,
    val rating: String?,
    val posterPreview: String?,
    val favoriteDate: Long
)
