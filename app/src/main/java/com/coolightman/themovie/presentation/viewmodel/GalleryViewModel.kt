package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieFramesUseCase
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val getMovieFramesUseCase: GetMovieFramesUseCase
) : ViewModel() {

    private var movieId: Long = 0

    val frames by lazy {
        getMovieFramesUseCase(movieId)
    }

    fun setMovieId(movieId: Long) {
        this.movieId = movieId
    }
}