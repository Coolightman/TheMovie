package com.coolightman.themovie.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.SimilarsDao
import com.coolightman.themovie.data.mapper.SimilarMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.domain.repository.SimilarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SimilarRepositoryImpl @Inject constructor(
    private val similarsDao: SimilarsDao,
    private val apiService: ApiService,
    private val mapper: SimilarMapper
) : SimilarRepository {

    override fun getMovieSimilars(movieId: Long): LiveData<List<ShortMovie>> = liveData{
        withContext(Dispatchers.IO) {
            if (!similarsDao.exists(movieId)) {
                loadSimilarsFromApi(movieId)
            }
            val list = Transformations.map(similarsDao.getSimilars(movieId)) {
                mapper.mapDbModelToListShortMovie(it)
            }
            emitSource(list)
        }
    }

    private suspend fun loadSimilarsFromApi(movieId: Long) {
        val similarsDto = apiService.loadSimilars(movieId)
        val similarsDbModel = mapper.mapDtoToDbModel(similarsDto, movieId)
        Log.d("LoadedSimilars", similarsDbModel.toString())
        similarsDao.insert(similarsDbModel)
    }
}