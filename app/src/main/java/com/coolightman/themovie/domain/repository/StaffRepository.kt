package com.coolightman.themovie.domain.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.domain.entity.Staff

interface StaffRepository {

    fun getMovieStaff(movieId: Long): LiveData<List<Staff>>
}