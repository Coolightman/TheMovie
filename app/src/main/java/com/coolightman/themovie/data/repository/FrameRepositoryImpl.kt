package com.coolightman.themovie.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
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

    override fun getMovieFrames(movieId: Long): LiveData<List<Frame>> = liveData {
        if (!framesDao.exists(movieId)) {
            loadFramesFromApi(movieId)
        }
        val list = Transformations.map(framesDao.getFrames(movieId)) {
            mapper.mapDbModelToListOfFrame(it)
        }
        emitSource(list)
    }

    private suspend fun loadFramesFromApi(movieId: Long) {
        try {
            val framesDto = apiService.loadFrames(movieId)
            val frameDbModel = mapper.mapDtoToDbModel(framesDto, movieId)
            framesDao.insert(frameDbModel)
        } catch (e: Exception) {
            Log.e("LoadFramesFromApi", "Bad response frames in movie $movieId")
        }
    }
}