package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Frame
import com.coolightman.themovie.domain.repository.FrameRepository
import javax.inject.Inject

class FrameRepositoryImpl @Inject constructor() : FrameRepository {

    override fun getMovieFrames(movieId: Long): LiveData<List<Frame>> {
        TODO("Not yet implemented")
    }
}