package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.usecase.*
import com.coolightman.themovie.util.ParseCoroutineException
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
    private val clearTop250MoviesUseCase: ClearTop250MoviesUseCase,
    private val parseCoroutineException: ParseCoroutineException
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine exception", throwable.stackTraceToString())
        val errorMessage = parseCoroutineException.parseException(throwable)
        onError(errorMessage)
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _pageLoading = MutableLiveData<Boolean>()
    val pageLoading: LiveData<Boolean>
        get() = _pageLoading

    val popularMovies by lazy {
        getPopularMoviesUseCase()
    }

    val top250Movies by lazy {
        getTop250MoviesUseCase()
    }

    val favoriteMovies by lazy {
        getFavoriteMoviesUseCase()
    }

    fun loadPopularNextPage() {
        viewModelScope.launch(handler) {
            showProgress()
            val job = launch {
                loadPopularNextPageUseCase()
            }
            job.join()
            hideProgress()
        }
    }

    private fun hideProgress() {
        _pageLoading.postValue(false)
    }

    private fun showProgress() {
        _pageLoading.postValue(true)
    }

    fun loadTop250NextPage() {
        viewModelScope.launch(handler) {
            showProgress()
            val job = launch {
                loadTop250NextPageUseCase()
            }
            job.join()
            hideProgress()
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

    fun resetError() {
        _errorMessage.postValue("")
    }
}