package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.dao.VideosDao
import com.coolightman.themovie.data.mapper.VideoMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.Video
import com.coolightman.themovie.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videosDao: VideosDao,
    private val apiService: ApiService,
    private val mapper: VideoMapper
) : VideoRepository {

    override fun getMovieVideos(movieId: Long): LiveData<List<Video>> =
        Transformations.map(videosDao.getVideos(movieId)) {
            it?.let { mapper.mapDbModelToListOfVideo(it) } ?: emptyList()
        }

    override suspend fun fetchMovieVideos(movieId: Long) {
        if (!videosDao.exists(movieId)) {
            loadVideosFromApi(movieId)
        }
    }

    override suspend fun refreshMovieVideos(movieId: Long) {
        loadVideosFromApi(movieId)
    }

    private suspend fun loadVideosFromApi(movieId: Long) {
        val videosDto = apiService.loadVideos(movieId)
        val videosDbModel = mapper.mapDtoToDbModel(videosDto, movieId)
        videosDao.insert(videosDbModel)
    }
}