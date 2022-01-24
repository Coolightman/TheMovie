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
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factsDao: FactsDao,
    private val apiService: ApiService,
    private val mapper: FactMapper
) : FactRepository {

    override fun getMovieFacts(movieId: Long): LiveData<List<Fact>> = liveData {
        if (!factsDao.exists(movieId)) {
            loadFactsFromApi(movieId)
        }
        val list = Transformations.map(factsDao.getFacts(movieId)) {
            mapper.mapDbModelToListOfFact(it)
        }
        emitSource(list)
    }

    private suspend fun loadFactsFromApi(movieId: Long) {
        try {
            val factsDto = apiService.loadFacts(movieId)
            val factsDbModel = mapper.mapDtoToDbModel(factsDto, movieId)
            factsDao.insert(factsDbModel)
        } catch (e: Exception) {
            Log.e("LoadFactsFromApi", "Bad request facts in movie $movieId")
        }
    }
}