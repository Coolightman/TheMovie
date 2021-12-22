package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.SimilarRepository
import javax.inject.Inject

class GetMovieSimilarsUseCase @Inject constructor(
    private val repository: SimilarRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieSimilars(movieId)
}