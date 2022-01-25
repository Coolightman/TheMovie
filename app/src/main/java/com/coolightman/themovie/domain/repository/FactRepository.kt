package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Fact

interface FactRepository {

    fun getMovieFacts(movieId: Long): LiveData<List<Fact>>
    suspend fun fetchMovieFacts(movieId: Long)
}