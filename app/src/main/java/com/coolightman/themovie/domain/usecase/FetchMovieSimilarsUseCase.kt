package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.SimilarRepository
import javax.inject.Inject

class FetchMovieSimilarsUseCase @Inject constructor(
    private val repository: SimilarRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.fetchMovieSimilars(movieId)
}