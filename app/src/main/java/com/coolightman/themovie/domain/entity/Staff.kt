package com.coolightman.themovie.domain.entity

data class Staff(
    val staffId: Long,
    val nameRu: String,
    val nameEn: String,
    val alias: String,
    val posterUrl: String,
    val professionText: String
)
