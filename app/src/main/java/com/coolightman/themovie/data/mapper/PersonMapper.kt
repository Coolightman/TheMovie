package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.PersonDbModel
import com.coolightman.themovie.data.database.dbModel.PersonFilmDbModel
import com.coolightman.themovie.data.network.dto.PersonDto
import com.coolightman.themovie.data.network.dto.PersonFilmsDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.Person
import com.coolightman.themovie.domain.entity.PersonFilm
import com.coolightman.themovie.util.TextFormat.convertData
import com.coolightman.themovie.util.TextFormat.convertGrowth
import javax.inject.Inject

@ApplicationScope
class PersonMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: PersonDto) = PersonDbModel(
        personId = dto.personId,
        nameRu = dto.nameRu,
        nameEn = dto.nameEn,
        sex = dto.sex,
        posterUrl = dto.posterUrl,
        growth = convertGrowth(dto.growth),
        birthday = convertData(dto.birthday),
        death = convertData(dto.death),
        age = dto.age,
        birthplace = dto.birthplace,
        deathplace = dto.deathplace,
        profession = dto.profession,
        facts = dto.facts,
        films = mapFilmsDtoToDbModels(dto.films)
    )

    private fun mapFilmsDtoToDbModels(filmsDto: List<PersonFilmsDto>): List<PersonFilmDbModel> {
        return filmsDto.map { mapFilmDtoToDbModel(it) }
    }

    private fun mapFilmDtoToDbModel(dto: PersonFilmsDto) = PersonFilmDbModel(
        movieId = dto.movieId,
        nameRu = dto.nameRu,
        nameEn = dto.nameEn,
        rating = dto.rating,
        description = dto.description,
        professionKey = dto.professionKey
    )

    fun mapDbModelToEntity(dbModel: PersonDbModel) = Person(
        personId = dbModel.personId,
        nameRu = dbModel.nameRu,
        nameEn = dbModel.nameEn,
        sex = dbModel.sex,
        posterUrl = dbModel.posterUrl,
        growth = dbModel.growth,
        birthday = dbModel.birthday,
        death = dbModel.death,
        age = dbModel.age?.toString(),
        birthplace = dbModel.birthplace,
        deathplace = dbModel.deathplace,
        profession = dbModel.profession,
        facts = dbModel.facts,
        films = mapFilmsDbModelToEntities(dbModel.films)
    )

    private fun mapFilmsDbModelToEntities(filmsDbModel: List<PersonFilmDbModel>): List<PersonFilm> {
        return filmsDbModel.map { mapFilmDbModelToEntity(it) }
    }

    private fun mapFilmDbModelToEntity(dbModel: PersonFilmDbModel) = PersonFilm(
        movieId = dbModel.movieId,
        nameRu = dbModel.nameRu,
        nameEn = dbModel.nameEn,
        rating = dbModel.rating,
        description = dbModel.description,
        professionKey = dbModel.professionKey
    )

}