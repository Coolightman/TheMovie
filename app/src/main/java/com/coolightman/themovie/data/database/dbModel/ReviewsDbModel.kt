package com.coolightman.themovie.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReviewsDbModel(
    @PrimaryKey
    val movieId: Long,
    val items: List<ReviewDbModel>
)
