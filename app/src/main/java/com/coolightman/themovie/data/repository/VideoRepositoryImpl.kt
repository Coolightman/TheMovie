package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.data.database.dao.VideosDao
import com.coolightman.themovie.data.network.ApiClient
import com.coolightman.themovie.domain.entity.Video
import com.coolightman.themovie.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videosDao: VideosDao
) : VideoRepository {

    private val api = ApiClient.getApiService()

    override fun getMovieVideos(movieId: Long): LiveData<List<Video>> {
        TODO("Not yet implemented")
    }
}