package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository

class GetTop250MoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getTop250Movies()
}