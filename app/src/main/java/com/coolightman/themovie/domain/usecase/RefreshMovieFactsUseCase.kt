package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FactRepository
import javax.inject.Inject

class RefreshMovieFactsUseCase @Inject constructor(
    private val repository: FactRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.refreshMovieFacts(movieId)
}