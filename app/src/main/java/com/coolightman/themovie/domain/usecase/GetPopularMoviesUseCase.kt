package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getPopularMovies()
}