package com.coolightman.themovie.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.FactsDao
import com.coolightman.themovie.data.mapper.FactMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.Fact
import com.coolightman.themovie.domain.repository.FactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factsDao: FactsDao,
    private val apiService: ApiService,
    private val mapper: FactMapper
) : FactRepository {

    override fun getMovieFacts(movieId: Long): LiveData<List<Fact>> = liveData {
        withContext(Dispatchers.IO) {
            if (!factsDao.exists(movieId)) {
                loadFactsFromApi(movieId)
            }
            val list = Transformations.map(factsDao.getFacts(movieId)) {
                mapper.mapDbModelToListOfFact(it)
            }
            emitSource(list)
        }
    }

    private suspend fun loadFactsFromApi(movieId: Long) {
        val factsDto = apiService.loadFacts(movieId)
        val factsDbModel = mapper.mapDtoToDbModel(factsDto, movieId)
        Log.d("LoadedFacts", factsDbModel.toString())
        factsDao.insert(factsDbModel)
    }
}