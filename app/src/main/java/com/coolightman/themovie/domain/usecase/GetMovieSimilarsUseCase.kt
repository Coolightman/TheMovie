package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.SimilarRepository

class GetMovieSimilarsUseCase(
    private val repository: SimilarRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieSimilars(movieId)
}