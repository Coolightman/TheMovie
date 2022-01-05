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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FrameRepositoryImpl @Inject constructor(
    private val framesDao: FramesDao,
    private val apiService: ApiService,
    private val mapper: FrameMapper
) : FrameRepository {

    override fun getMovieFrames(movieId: Long): LiveData<List<Frame>> = liveData {
        withContext(Dispatchers.IO) {
            if (!framesDao.exists(movieId)) {
                loadFramesFromApi(movieId)
            }
            val list = Transformations.map(framesDao.getFrames(movieId)) {
                mapper.mapDbModelToListOfFrame(it)
            }
            emitSource(list)
        }
    }

    private suspend fun loadFramesFromApi(movieId: Long) {
        val framesDto = apiService.loadFrames(movieId)
        val frameDbModel = mapper.mapDtoToDbModel(framesDto, movieId)
        Log.d("LoadedFrames", frameDbModel.toString())
        framesDao.insert(frameDbModel)
    }
}