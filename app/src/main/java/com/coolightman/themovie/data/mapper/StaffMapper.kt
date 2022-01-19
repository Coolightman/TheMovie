package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.StaffDbModel
import com.coolightman.themovie.data.network.dto.StaffDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.Staff
import javax.inject.Inject

@ApplicationScope
class StaffMapper @Inject constructor() {

    fun mapDtoListToDbModelList(dto: List<StaffDto>, movieId: Long): List<StaffDbModel> {
        val dbModelsList = mutableListOf<StaffDbModel>()
        for ((i, item) in dto.withIndex()) {
            val dbModel = StaffDbModel(
                staffId = item.staffId,
                movieId = movieId,
                importanceNumber = i,
                nameRu = item.nameRu,
                nameEn = item.nameEn,
                alias = item.description,
                posterUrl = item.posterUrl,
                professionText = item.professionText,
                professionKey = item.professionKey
            )
            dbModelsList.add(dbModel)
        }
        return dbModelsList
    }

    fun mapDbModelListToEntityList(list: List<StaffDbModel>): List<Staff> {
        return list.map { mapDbModelToEntity(it) }
    }

    private fun mapDbModelToEntity(dbModel: StaffDbModel) = Staff(
        staffId = dbModel.staffId,
        nameRu = dbModel.nameRu ?: EMPTY_STRING,
        nameEn = dbModel.nameEn ?: EMPTY_STRING,
        alias = dbModel.alias ?: EMPTY_STRING,
        posterUrl = dbModel.posterUrl ?: EMPTY_STRING,
        professionText = dbModel.professionText ?: EMPTY_STRING
    )

    companion object {
        private const val EMPTY_STRING = ""
    }
}