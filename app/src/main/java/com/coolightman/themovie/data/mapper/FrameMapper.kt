package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.FrameDbModel
import com.coolightman.themovie.data.database.dbModel.FramesDbModel
import com.coolightman.themovie.data.network.dto.FrameDto
import com.coolightman.themovie.data.network.dto.FramesDto
import com.coolightman.themovie.domain.entity.Frame

class FrameMapper {

    fun mapDtoToDbModel(dto: FramesDto, movieId: Long) = FramesDbModel(
        movieId = movieId,
        items = dto.items
            .filter { it.imageUrl != null && it.previewUrl != null }
            .map { mapFrameDtoToDbModel(it) }
    )

    private fun mapFrameDtoToDbModel(dto: FrameDto) = FrameDbModel(
        image = dto.imageUrl!!,
        imagePreview = dto.previewUrl!!
    )

    fun mapDbModelToListOfFrame(dbModel: FramesDbModel): List<Frame> {
        return dbModel.items.map { Frame(it.image, it.imagePreview) }
    }
}