package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.usecase.AddMovieToFavoriteUseCase
import com.coolightman.themovie.domain.usecase.GetMovieUseCase
import com.coolightman.themovie.domain.usecase.LoadMovieDetailsUseCase
import com.coolightman.themovie.domain.usecase.RemoveMovieFromFavoriteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val loadMovieDetailsUseCase: LoadMovieDetailsUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", "$throwable")
    }

    fun getMovie(movieId: Long) = getMovieUseCase(movieId)

    suspend fun loadMovieDetails(movieId: Long) {
        viewModelScope.launch(handler) {
            loadMovieDetailsUseCase(movieId)
        }
    }

    fun addMovieToFavorite(movieId: Long){
        viewModelScope.launch(handler) {
            addMovieToFavoriteUseCase(movieId)
        }
    }

    fun removeMovieFromFavorite(movieId: Long){
        viewModelScope.launch(handler) {
            removeMovieFromFavoriteUseCase(movieId)
        }
    }

}