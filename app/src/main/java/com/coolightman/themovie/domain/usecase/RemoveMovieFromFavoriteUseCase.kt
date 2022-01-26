package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.data.database.dbModel.MovieDbModel
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.domain.repository.FavoriteRepository
import com.coolightman.themovie.domain.repository.MovieRepository
import com.coolightman.themovie.domain.repository.ShortMovieRepository
import javax.inject.Inject

class RemoveMovieFromFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val shortMovieRepository: ShortMovieRepository,
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(movieId: Long) {
        val movie = movieRepository.getMovieDbModel(movieId)
        setMovieFromFavorite(movie)
        favoriteRepository.removeMovieFromFavorite(movieId)
        setShortMovieFromFavorite(movieId)
    }

    private suspend fun setMovieFromFavorite(movie: MovieDbModel) {
        movie.isFavorite = false
        movieRepository.insertMovieDbModel(movie)
    }

    private suspend fun setShortMovieFromFavorite(movieId: Long) {
        val shortMovie = shortMovieRepository.getShortMovieDbModel(movieId)
        shortMovie?.let {
            it.isFavorite = false
            shortMovieRepository.insertShortMovieDbModel(it)
        }
    }
}