package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieFramesUseCase
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val getMovieFramesUseCase: GetMovieFramesUseCase
) : ViewModel() {

    fun getFrames(movieId: Long) = getMovieFramesUseCase(movieId)
}