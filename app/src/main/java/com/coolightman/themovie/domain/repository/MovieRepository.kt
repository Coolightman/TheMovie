package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.data.database.dbModel.MovieDbModel
import com.coolightman.themovie.domain.entity.Movie

interface MovieRepository {

    fun getMovie(movieId: Long): LiveData<Movie>
    suspend fun fetchMovie(movieId: Long)
    suspend fun getMovieDbModel(movieId: Long): MovieDbModel
    suspend fun insertMovieDbModel(movieDbModel: MovieDbModel)
}