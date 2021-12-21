package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveMovieFromFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.removeMovieFromFavorite(movieId)
}