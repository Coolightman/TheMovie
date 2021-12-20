package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FactRepository

class GetMovieFactsUseCase(
    private val repository: FactRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieFacts(movieId)
}