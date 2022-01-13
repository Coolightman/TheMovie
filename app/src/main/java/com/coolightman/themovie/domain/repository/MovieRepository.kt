package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.MovieSearch
import com.coolightman.themovie.domain.entity.ShortMovie

interface MovieRepository {

    fun getPopularMovies(): LiveData<List<ShortMovie>>
    fun getTop250Movies(): LiveData<List<ShortMovie>>
    fun getFavoriteMovies(): LiveData<List<Movie>>
    fun getMovieSearchList(): LiveData<List<MovieSearch>>
    fun getMovie(movieId: Long): LiveData<Movie>
    fun getTop250Place(movieId: Long): LiveData<String>
    suspend fun clearPopularMovies()
    suspend fun clearTop250Movies()
    suspend fun searchMovies(keywords: String)
}