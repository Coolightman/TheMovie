package com.coolightman.themovie.data.repository

import com.coolightman.themovie.data.database.dao.FavoriteDao
import com.coolightman.themovie.data.database.dao.MovieDao
import com.coolightman.themovie.data.database.dao.ShortMovieDao
import com.coolightman.themovie.data.database.dbModel.MovieDbModel
import com.coolightman.themovie.data.database.dbModel.ShortMovieDbModel
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
            setMovieIsFavorite(movie, true)
            createFavorite(movie)
            val shortMovie = shortMovieDao.getShortMovie(movieId)
            setShortMovieIsFavorite(shortMovie, true)
        }
    }

    private suspend fun setShortMovieIsFavorite(
        shortMovie: ShortMovieDbModel,
        isFavorite: Boolean
    ) {
        shortMovie.isFavorite = isFavorite
        shortMovieDao.insertShortMovie(shortMovie)
    }

    private suspend fun createFavorite(movie: MovieDbModel) {
        val favorite = mapper.mapMovieDbModelToFavoriteDbModel(movie)
        favoriteDao.insert(favorite)
    }

    private suspend fun setMovieIsFavorite(movie: MovieDbModel, isFavorite: Boolean) {
        movie.isFavorite = isFavorite
        movieDao.insert(movie)
    }

    override suspend fun removeMovieFromFavorite(movieId: Long) {
        withContext(Dispatchers.IO) {
            val movie = movieDao.getMovieModel(movieId)
            setMovieIsFavorite(movie, false)
            favoriteDao.remove(movieId)
            val shortMovie = shortMovieDao.getShortMovie(movieId)
            setShortMovieIsFavorite(shortMovie, false)
        }
    }
}