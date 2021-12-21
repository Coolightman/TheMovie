package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() = repository.getFavoriteMovies()
}