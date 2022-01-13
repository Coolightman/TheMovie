package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieSearchListUseCase
import javax.inject.Inject

class SearchMovieViewModel @Inject constructor(
    private val getMovieSearchListUseCase: GetMovieSearchListUseCase
) : ViewModel() {

    fun getMovieSearchList() = getMovieSearchListUseCase()

    fun searchMovies(keywords: String) {

    }
}