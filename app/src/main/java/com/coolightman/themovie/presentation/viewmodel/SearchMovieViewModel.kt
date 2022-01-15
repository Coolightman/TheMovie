package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.usecase.GetMovieSearchListUseCase
import com.coolightman.themovie.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMovieViewModel @Inject constructor(
    private val getMovieSearchListUseCase: GetMovieSearchListUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", "$throwable")
    }

    fun getMovieSearchList() = getMovieSearchListUseCase()

    fun searchMovies(keywords: String) {
        viewModelScope.launch(handler) { searchMoviesUseCase(keywords) }
    }
}