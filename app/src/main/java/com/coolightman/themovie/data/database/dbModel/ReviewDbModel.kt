package com.coolightman.themovie.data.database.dbModel

data class ReviewDbModel(
    val reviewId: Long,
    val type: String,
    val data: String,
    val author: String,
    val title: String,
    val description: String
)
