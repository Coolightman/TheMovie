package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.ShortMovie

interface ShortMovieRepository {

    fun getPopularMovies(): LiveData<List<ShortMovie>>
    fun getTop250Movies(): LiveData<List<ShortMovie>>
    fun getFavoriteMovies(): LiveData<List<ShortMovie>>
    fun getTop250Place(movieId: Long): LiveData<String>
    suspend fun clearPopularMovies()
    suspend fun clearTop250Movies()
}