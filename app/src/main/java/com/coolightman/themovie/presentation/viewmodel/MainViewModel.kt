package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.usecase.*
import com.coolightman.themovie.util.ParseException.parseException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTop250MoviesUseCase: GetTop250MoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val loadPopularNextPageUseCase: LoadPopularNextPageUseCase,
    private val loadTop250NextPageUseCase: LoadTop250NextPageUseCase,
    private val clearPopularMoviesUseCase: ClearPopularMoviesUseCase,
    private val clearTop250MoviesUseCase: ClearTop250MoviesUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine exception", throwable.stackTraceToString())
        val errorMessage = parseException(throwable)
        onError(errorMessage)
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getPopularMovies() = getPopularMoviesUseCase()
    fun getTop250Movies() = getTop250MoviesUseCase()
    fun getFavoriteMovies() = getFavoriteMoviesUseCase()


    fun loadPopularNextPage() {
        viewModelScope.launch(handler) {
            loadPopularNextPageUseCase()
        }
    }

    fun loadTop250NextPage() {
        viewModelScope.launch(handler) {
            loadTop250NextPageUseCase()
        }
    }

    fun refreshPopularMovies() {
        viewModelScope.launch(handler) {
            val job = launch {
                clearPopularMoviesUseCase()
            }
            job.join()
            loadPopularNextPageUseCase()
        }
    }

    fun refreshTop250Movies() {
        viewModelScope.launch(handler) {
            val job = launch {
                clearTop250MoviesUseCase()
            }
            job.join()
            loadTop250NextPageUseCase()
        }
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
    }
}