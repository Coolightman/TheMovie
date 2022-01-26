package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieReviewsUseCase
import javax.inject.Inject

class AllReviewsViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    private var movieId: Long = 0

    val reviews by lazy {
        getMovieReviewsUseCase(movieId)
    }

    fun setMovieId(movieId: Long) {
        this.movieId = movieId
    }
}