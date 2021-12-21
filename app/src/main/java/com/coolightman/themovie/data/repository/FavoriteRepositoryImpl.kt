package com.coolightman.themovie.data.repository

import com.coolightman.themovie.data.database.dao.FavoriteDao
import com.coolightman.themovie.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun addMovieToFavorite(movieId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun removeMovieFromFavorite(movieId: Long) {
        TODO("Not yet implemented")
    }
}