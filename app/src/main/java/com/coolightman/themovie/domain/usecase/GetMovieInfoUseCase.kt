package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository

class GetMovieInfoUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieInfo(movieId)
}