package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieStaffUseCase
import javax.inject.Inject

class StaffViewModel @Inject constructor(
    private val getMovieStaffUseCase: GetMovieStaffUseCase
) : ViewModel() {

    fun getStaff(movieId:Long) = getMovieStaffUseCase(movieId)
}