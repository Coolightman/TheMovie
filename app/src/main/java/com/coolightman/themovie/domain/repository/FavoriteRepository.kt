package com.coolightman.themovie.domain.repository

import com.coolightman.themovie.data.database.dbModel.FavoriteDbModel

interface FavoriteRepository {

    suspend fun removeMovieFromFavorite(movieId: Long)
    suspend fun insertFavoriteDbModel(favoriteDbModel: FavoriteDbModel)
}