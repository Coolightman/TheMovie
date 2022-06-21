package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolightman.themovie.domain.entity.*
import com.coolightman.themovie.domain.usecase.*
import com.coolightman.themovie.util.ParseCoroutineException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val fetchMovieUseCase: FetchMovieUseCase,
    private val fetchMovieNCUseCase: FetchMovieNCUseCase,
    private val getMovieFramesUseCase: GetMovieFramesUseCase,
    private val fetchMovieFramesUseCase: FetchMovieFramesUseCase,
    private val getMovieFactsUseCase: GetMovieFactsUseCase,
    private val fetchMovieFactsUseCase: FetchMovieFactsUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val fetchMovieVideosUseCase: FetchMovieVideosUseCase,
    private val getMovieStaffUseCase: GetMovieStaffUseCase,
    private val fetchMovieStaffUseCase: FetchMovieStaffUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val fetchMovieReviewsUseCase: FetchMovieReviewsUseCase,
    private val getMovieSimilarsUseCase: GetMovieSimilarsUseCase,
    private val fetchMovieSimilarsUseCase: FetchMovieSimilarsUseCase,
    private val getTop250PlaceUseCase: GetTop250PlaceUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase,
    private val parseCoroutineException: ParseCoroutineException
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", throwable.stackTraceToString())
        val errorMessage = parseCoroutineException.parseException(throwable)
        onError(errorMessage)
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

    fun fetchMovieData(movieId: Long) {
        this.movieId = movieId
        fetchMovie()
        fetchFrames()
        fetchFacts()
        fetchVideos()
        fetchStaff()
        fetchReviews()
        fetchSimilars()
    }

    fun fetchMovieDataNotCheck(movieId: Long) {
        this.movieId = movieId
        fetchMovieNC()
        fetchFrames()
        fetchFacts()
        fetchVideos()
        fetchStaff()
        fetchReviews()
        fetchSimilars()
    }

    private fun fetchMovieNC() {
        viewModelScope.launch(handler) {
            fetchMovieNCUseCase(movieId)
        }
    }

    private fun fetchMovie() {
        viewModelScope.launch(handler) {
            fetchMovieUseCase(movieId)
        }
    }

    private fun fetchFrames() {
        viewModelScope.launch(handler) {
            fetchMovieFramesUseCase(movieId)
        }
    }

    private fun fetchFacts() {
        viewModelScope.launch(handler) {
            fetchMovieFactsUseCase(movieId)
        }
    }

    private fun fetchVideos() {
        viewModelScope.launch(handler) {
            fetchMovieVideosUseCase(movieId)
        }
    }

    private fun fetchStaff() {
        viewModelScope.launch(handler) {
            fetchMovieStaffUseCase(movieId)
        }
    }

    private fun fetchReviews() {
        viewModelScope.launch(handler) {
            fetchMovieReviewsUseCase(movieId)
        }
    }

    private fun fetchSimilars() {
        viewModelScope.launch(handler) {
            fetchMovieSimilarsUseCase(movieId)
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