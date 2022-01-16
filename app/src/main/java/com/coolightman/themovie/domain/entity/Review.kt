package com.coolightman.themovie.domain.entity

data class Review(
    val reviewId: Long,
    val type: ReviewType,
    val data: String,
    val author: String,
    val title: String,
    val description: String
)
