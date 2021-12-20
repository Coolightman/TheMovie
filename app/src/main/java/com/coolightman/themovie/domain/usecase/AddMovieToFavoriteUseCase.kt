package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FavoriteRepository

class AddMovieToFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(movieId: Long) = repository.addMovieToFavorite(movieId)
}