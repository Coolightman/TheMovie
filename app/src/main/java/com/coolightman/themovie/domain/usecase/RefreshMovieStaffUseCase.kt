package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.StaffRepository
import javax.inject.Inject

class RefreshMovieStaffUseCase @Inject constructor(
    private val repository: StaffRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.refreshMovieStaff(movieId)
}