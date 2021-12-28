package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Fact
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.ShortMovie

interface MovieRepository {

    fun getPopularMovies(): LiveData<List<ShortMovie>>
    fun getTop250Movies(): LiveData<List<ShortMovie>>
    fun getFavoriteMovies(): LiveData<List<Movie>>
    suspend fun getMovieInfo(movieId: Long): LiveData<Movie>
    suspend fun clearPopularMovies()
    suspend fun clearTop250Movies()
}