package com.coolightman.themovie.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.MovieDatabase
import com.coolightman.themovie.data.mapper.VideoMapper
import com.coolightman.themovie.data.network.ApiClient
import com.coolightman.themovie.domain.entity.Video
import com.coolightman.themovie.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    context: Context
) : VideoRepository {

    private val database = MovieDatabase.getDatabase(context).videosDao()
    private val api = ApiClient.getApiService()

    override fun getMovieVideos(movieId: Long): LiveData<List<Video>> {
//        TODO
        return Transformations.map(database.getVideosLiveData(movieId)) {
            VideoMapper().mapDbModelToListOfVideo(it)
        }
    }
}