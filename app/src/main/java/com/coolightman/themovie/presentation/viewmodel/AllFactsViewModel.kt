package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieFactsUseCase
import javax.inject.Inject

class AllFactsViewModel @Inject constructor(
    private val getMovieFactsUseCase: GetMovieFactsUseCase
) : ViewModel() {

    fun getFacts(movieId: Long) = getMovieFactsUseCase(movieId)
}