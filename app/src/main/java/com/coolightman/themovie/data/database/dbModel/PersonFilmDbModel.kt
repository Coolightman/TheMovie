package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coolightman.themovie.data.network.dto.PersonFilmsDto
import com.google.gson.annotations.SerializedName

@Entity
data class PersonFilmDbModel(
    @PrimaryKey
    val movieId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val description: String?,
    val professionKey: String?
)
