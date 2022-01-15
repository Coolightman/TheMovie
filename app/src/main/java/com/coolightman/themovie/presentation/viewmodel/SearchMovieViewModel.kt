package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.usecase.GetMovieSearchListUseCase
import com.coolightman.themovie.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMovieViewModel @Inject constructor(
    private val getMovieSearchListUseCase: GetMovieSearchListUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    fun getMovieSearchList() = getMovieSearchListUseCase()

    fun searchMovies(keywords: String) {
        viewModelScope.launch { searchMoviesUseCase(keywords) }
    }
}