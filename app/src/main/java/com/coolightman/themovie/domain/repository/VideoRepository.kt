package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Video

interface VideoRepository {

    fun getMovieVideos(movieId: Long): LiveData<List<Video>>
    suspend fun fetchMovieVideos(movieId: Long)
    suspend fun refreshMovieVideos(movieId: Long)
}