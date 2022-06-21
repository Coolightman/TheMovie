package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Frame

interface FrameRepository {

    fun getMovieFrames(movieId: Long): LiveData<List<Frame>>
    suspend fun fetchMovieFrames(movieId: Long)
    suspend fun refreshMovieFrames(movieId: Long)
}