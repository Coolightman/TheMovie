package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(movieId: Long) = repository.addMovieToFavorite(movieId)
}