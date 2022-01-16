package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieReviewsUseCase
import javax.inject.Inject

class ReviewViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    fun getReviews(movieId: Long) = getMovieReviewsUseCase(movieId)
}