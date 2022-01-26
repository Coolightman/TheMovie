package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.data.database.dbModel.MovieDbModel
import com.coolightman.themovie.data.mapper.MovieMapper
import com.coolightman.themovie.domain.repository.FavoriteRepository
import com.coolightman.themovie.domain.repository.MovieRepository
import com.coolightman.themovie.domain.repository.ShortMovieRepository
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val shortMovieRepository: ShortMovieRepository,
    private val favoriteRepository: FavoriteRepository,
    private val mapper: MovieMapper
) {

    suspend operator fun invoke(movieId: Long) {
        val movie = movieRepository.getMovieDbModel(movieId)
        setMovieToFavorite(movie)
        createFavorite(movie)
        setShortMovieToFavorite(movieId)
    }

    private suspend fun setMovieToFavorite(movie: MovieDbModel) {
        movie.isFavorite = true
        movieRepository.insertMovieDbModel(movie)
    }

    private suspend fun createFavorite(movie: MovieDbModel) {
        val favorite = mapper.mapMovieDbModelToFavoriteDbModel(movie)
        favoriteRepository.insertFavoriteDbModel(favorite)
    }

    private suspend fun setShortMovieToFavorite(movieId: Long) {
        val shortMovie = shortMovieRepository.getShortMovieDbModel(movieId)
        shortMovie?.let {
            it.isFavorite = true
            shortMovieRepository.insertShortMovieDbModel(it)
        }
    }
}