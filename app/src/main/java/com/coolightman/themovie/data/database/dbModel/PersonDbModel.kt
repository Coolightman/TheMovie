package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonDbModel(
    @PrimaryKey
    val personId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val sex: String?,
    val posterUrl: String?,
    val growth: Int?,
    val birthday: String?,
    val death: String?,
    val age: Int?,
    val birthplace: String?,
    val deathplace: String?,
    val profession: String?,
    val facts: List<String> = arrayListOf(),
    val films: List<PersonFilmDbModel> = arrayListOf()
)
