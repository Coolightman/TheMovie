package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Fact

interface FactRepository {

    suspend fun getMovieFacts(movieId: Long): LiveData<List<Fact>>
}