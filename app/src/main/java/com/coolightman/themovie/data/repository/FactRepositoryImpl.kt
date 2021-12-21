package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.data.database.dao.FactsDao
import com.coolightman.themovie.domain.entity.Fact
import com.coolightman.themovie.domain.repository.FactRepository
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val factsDao: FactsDao
) : FactRepository {

    override suspend fun getMovieFacts(movieId: Long): LiveData<List<Fact>> {
        TODO("Not yet implemented")
    }
}