package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.coolightman.themovie.data.database.dao.MovieDao
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.data.network.ApiService
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: ApiService,
    private val movieMapper: MovieMapper
) : MovieRepository {

    override fun getMovie(movieId: Long): LiveData<Movie> = liveData {
        if (!movieDao.exists(movieId)) {
            loadMovieFromApi(movieId)
        }
        val movie = Transformations.map(movieDao.getMovie(movieId)) {
            movieMapper.mapDbModelToEntity(it)
        }
        emitSource(movie)
    }

    private suspend fun loadMovieFromApi(movieId: Long) {
        val movieDto = apiService.loadMovie(movieId)
        val movieDbModel = movieMapper.mapDtoToDbModel(movieDto)
        movieDao.insert(movieDbModel)
    }
}