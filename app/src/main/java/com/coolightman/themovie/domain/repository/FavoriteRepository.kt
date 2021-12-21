package com.coolightman.themovie.domain.repository

interface FavoriteRepository {

    suspend fun addMovieToFavorite(movieId: Long)
    suspend fun removeMovieFromFavorite(movieId: Long)
}