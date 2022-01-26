package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

    override fun getMovieFacts(movieId: Long): LiveData<List<Fact>> =
        Transformations.map(factsDao.getFacts(movieId)) {
            it?.let { mapper.mapDbModelToListOfFact(it) } ?: emptyList()
        }

    override suspend fun fetchMovieFacts(movieId: Long) {
        if (!factsDao.exists(movieId)) {
            loadFactsFromApi(movieId)
        }
    }

    private suspend fun loadFactsFromApi(movieId: Long) {
        val factsDto = apiService.loadFacts(movieId)
        val factsDbModel = mapper.mapDtoToDbModel(factsDto, movieId)
        factsDao.insert(factsDbModel)
    }
}