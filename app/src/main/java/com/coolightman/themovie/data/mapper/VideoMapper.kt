package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.VideoDbModel
import com.coolightman.themovie.data.database.dbModel.VideosDbModel
import com.coolightman.themovie.data.network.dto.VideoDto
import com.coolightman.themovie.data.network.dto.VideosDto
import com.coolightman.themovie.domain.entity.Video

class VideoMapper {

    fun mapDtoToDbModel(dto: VideosDto, movieId: Long) = VideosDbModel(
        movieId = movieId,
        items = dto.items
            .filter { it.url != null && it.name != null }
            .map { mapVideoDtoToDbModel(it) }
    )

    private fun mapVideoDtoToDbModel(dto: VideoDto) = VideoDbModel(
        url = dto.url!!,
        name = dto.name!!
    )

    fun mapDbModelToListOfVideo(dbModel: VideosDbModel): List<Video> {
        return dbModel.items.map { Video(url = it.url, name = it.name) }
    }
}