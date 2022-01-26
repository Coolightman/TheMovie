package com.coolightman.themovie.domain.entity

data class Person(
    val personId: Long,
    val nameRu: String?,
    val nameEn: String?,
    val sex: String?,
    val posterUrl: String?,
    val growth: String?,
    val birthday: String?,
    val death: String?,
    val age: String?,
    val birthplace: String?,
    val deathplace: String?,
    val profession: String?,
    val facts: List<String> = arrayListOf(),
    val films: List<PersonFilm> = arrayListOf()
)
