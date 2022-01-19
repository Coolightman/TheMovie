package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class StaffDbModel(
    @PrimaryKey
    val staffId: Long,
    val movieId: Long,
    val importanceNumber: Int,
    val nameRu: String?,
    val nameEn: String?,
    val alias: String?,
    val posterUrl: String?,
    val professionText: String?,
    val professionKey: String?
)
