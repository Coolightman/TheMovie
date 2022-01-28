package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieFramesUseCase
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val getMovieFramesUseCase: GetMovieFramesUseCase
) : ViewModel() {

    private var movieId: Long = 0

    private val _lastPosition = MutableLiveData<Int>()
    val lastPosition: LiveData<Int>
        get() = _lastPosition

    val frames by lazy {
        getMovieFramesUseCase(movieId)
    }

    fun setMovieId(movieId: Long) {
        this.movieId = movieId
    }

    fun setPosition(position: Int) {
        _lastPosition.postValue(position)
    }
}