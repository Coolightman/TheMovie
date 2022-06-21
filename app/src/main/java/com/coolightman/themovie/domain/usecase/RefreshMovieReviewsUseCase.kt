package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.ReviewRepository
import javax.inject.Inject

class RefreshMovieReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.refreshMovieReviews(movieId)
}