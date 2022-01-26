package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.ShortMovie

interface SimilarRepository {

    fun getMovieSimilars(movieId: Long): LiveData<List<ShortMovie>>
    suspend fun fetchMovieSimilars(movieId: Long)
}