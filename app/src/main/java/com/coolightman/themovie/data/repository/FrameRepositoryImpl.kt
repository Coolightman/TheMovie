package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.dao.FramesDao
import com.coolightman.themovie.data.mapper.FrameMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.Frame
import com.coolightman.themovie.domain.repository.FrameRepository
import javax.inject.Inject

class FrameRepositoryImpl @Inject constructor(
    private val framesDao: FramesDao,
    private val apiService: ApiService,
    private val mapper: FrameMapper
) : FrameRepository {

    override fun getMovieFrames(movieId: Long): LiveData<List<Frame>> =
        Transformations.map(framesDao.getFrames(movieId)) {
            if (it != null) {
                mapper.mapDbModelToListOfFrame(it)
            } else {
                emptyList()
            }
        }

    override suspend fun fetchMovieFrames(movieId: Long) {
        if (!framesDao.exists(movieId)) {
            loadFramesFromApi(movieId)
        }
    }

    private suspend fun loadFramesFromApi(movieId: Long) {
        val framesDto = apiService.loadFrames(movieId)
        val frameDbModel = mapper.mapDtoToDbModel(framesDto, movieId)
        framesDao.insert(frameDbModel)
    }
}