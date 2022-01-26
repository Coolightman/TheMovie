package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieFactsUseCase
import javax.inject.Inject

class AllFactsViewModel @Inject constructor(
    private val getMovieFactsUseCase: GetMovieFactsUseCase
) : ViewModel() {

    private var movieId: Long = 0

    val facts by lazy {
        getMovieFactsUseCase(movieId)
    }

    fun setMovieId(movieId: Long) {
        this.movieId = movieId
    }
}