package com.coolightman.themovie.domain.repository

interface FavoriteRepository {

    fun addMovieToFavorite(movieId: Long)
    fun removeMovieFromFavorite(movieId: Long)
}