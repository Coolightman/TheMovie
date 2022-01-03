package com.coolightman.themovie.data.repository

import com.coolightman.themovie.data.database.dao.FavoriteDao
import com.coolightman.themovie.data.database.dao.MovieDao
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val movieDao: MovieDao,
    private val shortMovieDao: ShortMovieDao,
    private val mapper: MovieMapper
) : FavoriteRepository {

    override suspend fun addMovieToFavorite(movieId: Long) {
        withContext(Dispatchers.IO) {
            val movie = movieDao.getMovieModel(movieId)
            val shortMovie = shortMovieDao.getShortMovie(movieId)
            movie.isFavorite = true
            shortMovie.isFavorite = true
            val favorite = mapper.mapMovieDbModelToFavoriteDbModel(movie)
            movieDao.insert(movie)
            shortMovieDao.insertShortMovie(shortMovie)
            favoriteDao.insert(favorite)
        }
    }

    override suspend fun removeMovieFromFavorite(movieId: Long) {
        withContext(Dispatchers.IO) {
            val movie = movieDao.getMovieModel(movieId)
            val shortMovie = shortMovieDao.getShortMovie(movieId)
            movie.isFavorite = false
            shortMovie.isFavorite = false
            favoriteDao.remove(movieId)
            movieDao.insert(movie)
            shortMovieDao.insertShortMovie(shortMovie)
        }
    }
}