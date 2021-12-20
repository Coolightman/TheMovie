package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.FactDbModel
import com.coolightman.themovie.data.database.dbModel.FactsDbModel
import com.coolightman.themovie.data.network.dto.FactDto
import com.coolightman.themovie.data.network.dto.FactsDto
import com.coolightman.themovie.domain.entity.Fact

class FactMapper {

    fun mapDtoToDbModel(dto: FactsDto, movieId: Long) = FactsDbModel(
        movieId = movieId,
        items = dto.items
            .filter { it.text != null && it.spoiler != null }
            .map { mapFrameDtoToDbModel(it) }
    )

    private fun mapFrameDtoToDbModel(dto: FactDto) = FactDbModel(
        text = dto.text!!,
        spoiler = dto.spoiler!!
    )

    fun mapDbModelToListOfFact(dbModel: FactsDbModel): List<Fact> {
        return dbModel.items.map { Fact(text = it.text, spoiler = it.spoiler) }
    }
}