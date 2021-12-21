package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.ShortMovie

interface SimilarRepository {

    suspend fun getMovieSimilars(movieId: Long): LiveData<List<ShortMovie>>
}