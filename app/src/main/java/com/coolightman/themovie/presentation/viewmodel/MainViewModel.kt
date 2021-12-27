package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.usecase.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTop250MoviesUseCase: GetTop250MoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val loadPopularNextPageUseCase: LoadPopularNextPageUseCase,
    private val loadTop250NextPageUseCase: LoadTop250NextPageUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", "$throwable")
    }

    fun getPopularMovies() = getPopularMoviesUseCase()
    fun getTop250Movies() = getTop250MoviesUseCase()
    fun getFavoriteMovies() = getFavoriteMoviesUseCase()

    init {
        viewModelScope.launch(handler) {
            loadPopularNextPageUseCase()
            loadTop250NextPageUseCase()
        }
    }
}