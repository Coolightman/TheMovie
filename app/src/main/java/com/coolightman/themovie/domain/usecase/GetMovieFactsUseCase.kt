package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FactRepository
import javax.inject.Inject

class GetMovieFactsUseCase @Inject constructor(
    private val repository: FactRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieFacts(movieId)
}