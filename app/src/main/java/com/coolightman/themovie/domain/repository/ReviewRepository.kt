package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Fact
import com.coolightman.themovie.domain.entity.Review

interface ReviewRepository {

    fun getMovieReviews(movieId: Long): LiveData<List<Review>>
    suspend fun fetchMovieReviews(movieId: Long)
}