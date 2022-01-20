package com.coolightman.themovie.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.VideosDao
import com.coolightman.themovie.data.mapper.VideoMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.Frame
import com.coolightman.themovie.domain.entity.Video
import com.coolightman.themovie.domain.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videosDao: VideosDao,
    private val apiService: ApiService,
    private val mapper: VideoMapper
) : VideoRepository {

    override fun getMovieVideos(movieId: Long): LiveData<List<Video>> = liveData {
        withContext(Dispatchers.IO) {
            if (!videosDao.exists(movieId)) {
                loadVideosFromApi(movieId)
            }
            val list = Transformations.map(videosDao.getVideos(movieId)) {
                mapper.mapDbModelToListOfVideo(it)
            }
            emitSource(list)
        }
    }

    private suspend fun loadVideosFromApi(movieId: Long) {
        try {
            val videosDto = apiService.loadVideos(movieId)
            val videosDbModel = mapper.mapDtoToDbModel(videosDto, movieId)
            videosDao.insert(videosDbModel)
        } catch (e: Exception) {
            Log.e("LoadVideoFromApi", "Bad request video in movie $movieId")
        }
    }
}