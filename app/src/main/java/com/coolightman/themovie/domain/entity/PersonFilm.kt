package com.coolightman.themovie.domain.entity

data class PersonFilm(
    val movieId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val description: String?,
    val professionKey: String?
)
