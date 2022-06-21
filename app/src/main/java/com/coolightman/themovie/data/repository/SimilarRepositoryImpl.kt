package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.coolightman.themovie.data.database.dao.SimilarsDao
import com.coolightman.themovie.data.mapper.SimilarMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.domain.repository.SimilarRepository
import javax.inject.Inject

class SimilarRepositoryImpl @Inject constructor(
    private val similarsDao: SimilarsDao,
    private val apiService: ApiService,
    private val mapper: SimilarMapper
) : SimilarRepository {

    override fun getMovieSimilars(movieId: Long): LiveData<List<ShortMovie>> =
        Transformations.map(similarsDao.getSimilars(movieId)) {
            it?.let { mapper.mapDbModelToListShortMovie(it) } ?: emptyList()
        }

    override suspend fun fetchMovieSimilars(movieId: Long) {
        if (!similarsDao.exists(movieId)) {
            loadSimilarsFromApi(movieId)
        }
    }

    override suspend fun refreshMovieSimilars(movieId: Long) {
        loadSimilarsFromApi(movieId)
    }

    private suspend fun loadSimilarsFromApi(movieId: Long) {
        val similarsDto = apiService.loadSimilars(movieId)
        val similarsDbModel = mapper.mapDtoToDbModel(similarsDto, movieId)
        similarsDao.insert(similarsDbModel)
    }
}