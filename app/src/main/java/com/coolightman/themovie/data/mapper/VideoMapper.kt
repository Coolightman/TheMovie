package com.coolightman.themovie.data.mapper

import com.coolightman.themovie.data.database.dbModel.VideoDbModel
import com.coolightman.themovie.data.database.dbModel.VideosDbModel
import com.coolightman.themovie.data.network.dto.VideoDto
import com.coolightman.themovie.data.network.dto.VideosDto
import com.coolightman.themovie.di.ApplicationScope
import com.coolightman.themovie.domain.entity.Video
import javax.inject.Inject

@ApplicationScope
class VideoMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: VideosDto, movieId: Long) = VideosDbModel(
        movieId = movieId,
        items = dto.items
            .filter { isValid(it) && isYoutube(it) }
            .map { mapVideoDtoToDbModel(it) }
    )

    private fun isValid(it: VideoDto) =
        it.url != null && it.name != null

    private fun isYoutube(it: VideoDto) =
        it.site == ARG_YOUTUBE_SITE && it.url!!.contains("=")

    private fun mapVideoDtoToDbModel(dto: VideoDto) = VideoDbModel(
        url = dto.url!!,
        name = dto.name!!,
        previewUrl = getPreviewUrl(dto.url!!)
    )

    private fun getPreviewUrl(url: String): String {
        val videoId = url.split("=")[1]
        return String.format(YOUTUBE_PREVIEW_URL_NODE, videoId)
    }

    fun mapDbModelToListOfVideo(dbModel: VideosDbModel): List<Video> {
        return dbModel.items.map { Video(url = it.url, name = it.name, previewUrl = it.previewUrl) }
    }

    companion object{
        private const val ARG_YOUTUBE_SITE = "YOUTUBE"
        private const val YOUTUBE_PREVIEW_URL_NODE = "https://img.youtube.com/vi/%s/hqdefault.jpg"
    }
}