package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTop250MoviesUseCase: GetTop250MoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val loadPopularNextPageUseCase: LoadPopularNextPageUseCase,
    private val loadTop250NextPageUseCase: LoadTop250NextPageUseCase
) : ViewModel() {

    fun getPopularMovies() = getPopularMoviesUseCase()
    fun getTop250Movies() = getTop250MoviesUseCase()
    fun getFavoriteMovies() = getFavoriteMoviesUseCase()

    init {
        viewModelScope.launch {
            loadPopularNextPageUseCase()
        }
    }
}