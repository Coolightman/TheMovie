package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.MovieSearch

interface MovieSearchRepository {

    fun getMovieSearchList(): LiveData<List<MovieSearch>>
    suspend fun searchMovies(keywords: String)
}