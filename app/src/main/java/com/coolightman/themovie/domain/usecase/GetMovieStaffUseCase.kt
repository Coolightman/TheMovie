package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.StaffRepository
import javax.inject.Inject

class GetMovieStaffUseCase @Inject constructor(
    private val repository: StaffRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieStaff(movieId)
}