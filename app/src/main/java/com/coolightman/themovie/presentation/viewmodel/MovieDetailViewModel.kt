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
    private val getMovieFramesUseCase: GetMovieFramesUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getMovieFactsUseCase: GetMovieFactsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieSimilarsUseCase: GetMovieSimilarsUseCase,
    private val getMovieStaffUseCase: GetMovieStaffUseCase,
    private val getTop250PlaceUseCase: GetTop250PlaceUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", "$throwable")
    }

    private val _staff = MutableLiveData<List<Staff>>()
    val staff: LiveData<List<Staff>>
        get() = _staff

    fun getMovie(movieId: Long): LiveData<Movie> = getMovieUseCase(movieId)

    fun getFrames(movieId: Long): LiveData<List<Frame>> = getMovieFramesUseCase(movieId)

    fun getFacts(movieId: Long): LiveData<List<Fact>> = getMovieFactsUseCase(movieId)

    fun getReviews(movieId: Long): LiveData<List<Review>> = getMovieReviewsUseCase(movieId)

    fun getVideos(movieId: Long): LiveData<List<Video>> = getMovieVideosUseCase(movieId)

    fun getSimilars(movieId: Long): LiveData<List<ShortMovie>> = getMovieSimilarsUseCase(movieId)

    fun getStaff(movieId: Long): LiveData<List<Staff>> = getMovieStaffUseCase(movieId)

    fun getTop250Place(movieId: Long): LiveData<String> = getTop250PlaceUseCase(movieId)

    fun addMovieToFavorite(movieId: Long) {
        viewModelScope.launch(handler) {
            addMovieToFavoriteUseCase(movieId)
        }
    }

    fun removeMovieFromFavorite(movieId: Long) {
        viewModelScope.launch(handler) {
            removeMovieFromFavoriteUseCase(movieId)
        }
    }

}