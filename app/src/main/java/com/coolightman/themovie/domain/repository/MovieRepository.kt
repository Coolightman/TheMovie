package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.MovieSearch
import com.coolightman.themovie.domain.entity.ShortMovie

interface MovieRepository {

    fun getMovieSearchList(): LiveData<List<MovieSearch>>
    fun getMovie(movieId: Long): LiveData<Movie>
    suspend fun searchMovies(keywords: String)
}