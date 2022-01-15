package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coolightman.themovie.data.network.dto.CountryDto
import com.coolightman.themovie.data.network.dto.GenreDto

@Entity
data class MovieSearchDbModel(
    @PrimaryKey
    val movieId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val type: String?,
    val year: String?,
    val filmLength: String?,
    val rating: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)
