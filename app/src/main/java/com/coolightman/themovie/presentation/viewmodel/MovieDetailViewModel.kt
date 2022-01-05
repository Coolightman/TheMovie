package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.entity.Frame
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.usecase.AddMovieToFavoriteUseCase
import com.coolightman.themovie.domain.usecase.GetMovieFramesUseCase
import com.coolightman.themovie.domain.usecase.GetMovieUseCase
import com.coolightman.themovie.domain.usecase.RemoveMovieFromFavoriteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getMovieFramesUseCase: GetMovieFramesUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", "$throwable")
    }

    fun getMovie(movieId: Long): LiveData<Movie> = getMovieUseCase(movieId)

    fun getFrames(movieId: Long): LiveData<List<Frame>> = getMovieFramesUseCase(movieId)

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