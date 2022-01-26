package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieStaffUseCase
import javax.inject.Inject

class StaffViewModel @Inject constructor(
    private val getMovieStaffUseCase: GetMovieStaffUseCase
) : ViewModel() {

    private var movieId: Long = 0

    val staff by lazy {
        getMovieStaffUseCase(movieId)
    }

    fun setMovieId(movieId: Long) {
        this.movieId = movieId
    }
}