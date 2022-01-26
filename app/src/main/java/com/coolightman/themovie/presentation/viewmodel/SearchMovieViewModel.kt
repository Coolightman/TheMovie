package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.usecase.GetMovieSearchListUseCase
import com.coolightman.themovie.domain.usecase.SearchMoviesUseCase
import com.coolightman.themovie.util.ParseCoroutineException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMovieViewModel @Inject constructor(
    private val getMovieSearchListUseCase: GetMovieSearchListUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val parseCoroutineException: ParseCoroutineException
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", throwable.stackTraceToString())
        val errorMessage = parseCoroutineException.parseException(throwable)
        onError(errorMessage)
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _searchLoaded = MutableLiveData<Boolean>()
    val searchLoaded: LiveData<Boolean>
        get() = _searchLoaded

    val searchResult by lazy {
        getMovieSearchListUseCase()
    }

    fun searchMovies(keywords: String) {
        viewModelScope.launch(handler) {
            val job = launch { searchMoviesUseCase(keywords)  }
            job.join()
            _searchLoaded.postValue(true)
        }
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
    }

    fun resetError() {
        _errorMessage.postValue("")
    }
}