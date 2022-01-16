package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.ReviewRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieReviews(movieId)
}