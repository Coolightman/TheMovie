package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository

class GetFavoriteMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getFavoriteMovies()
}