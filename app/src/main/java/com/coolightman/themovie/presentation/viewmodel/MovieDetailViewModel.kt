package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.entity.*
import com.coolightman.themovie.domain.usecase.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val fetchMovieUseCase: FetchMovieUseCase,
    private val getMovieFramesUseCase: GetMovieFramesUseCase,
    private val fetchMovieFramesUseCase: FetchMovieFramesUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getMovieFactsUseCase: GetMovieFactsUseCase,
    private val fetchMovieFactsUseCase: FetchMovieFactsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieSimilarsUseCase: GetMovieSimilarsUseCase,
    private val getMovieStaffUseCase: GetMovieStaffUseCase,
    private val getTop250PlaceUseCase: GetTop250PlaceUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", throwable.stackTraceToString())
        onError(throwable.stackTraceToString())
    }

    private var movieId: Long = 0

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val movie: LiveData<Movie> by lazy {
        getMovieUseCase(movieId)
    }

    val frames: LiveData<List<Frame>> by lazy {
        getMovieFramesUseCase(movieId)
    }

    val facts: LiveData<List<Fact>> by lazy {
        getMovieFactsUseCase(movieId)
    }

    val videos: LiveData<List<Video>> by lazy {
        getMovieVideosUseCase(movieId)
    }

    val staff: LiveData<List<Staff>> by lazy {
        getMovieStaffUseCase(movieId)
    }

    val reviews: LiveData<List<Review>> by lazy {
        getMovieReviewsUseCase(movieId)
    }

    val similars: LiveData<List<ShortMovie>> by lazy {
        getMovieSimilarsUseCase(movieId)
    }

    val top250Place: LiveData<String> by lazy {
        getTop250PlaceUseCase(movieId)
    }

    fun setMovieId(movieId: Long) {
        this.movieId = movieId
    }

    fun fetchMovie() {
        viewModelScope.launch(handler) {
            fetchMovieUseCase(movieId)
        }
    }

    fun fetchFacts() {
        viewModelScope.launch(handler) {
            fetchMovieFactsUseCase(movieId)
        }
    }

    fun fetchFrames() {
        viewModelScope.launch(handler) {
            fetchMovieFramesUseCase(movieId)
        }
    }

    fun addMovieToFavorite() {
        viewModelScope.launch(handler) {
            addMovieToFavoriteUseCase(movieId)
        }
    }

    fun removeMovieFromFavorite() {
        viewModelScope.launch(handler) {
            removeMovieFromFavoriteUseCase(movieId)
        }
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
    }

    fun resetError() {
        _errorMessage.postValue("")
    }

}