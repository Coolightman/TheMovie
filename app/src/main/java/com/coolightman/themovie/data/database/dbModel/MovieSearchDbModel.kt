package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieSearchDbModel(
    @PrimaryKey
    val movieId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val type: String?,
    val year: Int?,
    val filmLength: String?,
    val rating: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)
