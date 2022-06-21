package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.SimilarRepository
import javax.inject.Inject

class RefreshMovieSimilarsUseCase @Inject constructor(
    private val repository: SimilarRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.refreshMovieSimilars(movieId)
}