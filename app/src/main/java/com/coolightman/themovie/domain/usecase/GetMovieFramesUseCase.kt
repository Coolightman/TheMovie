package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FrameRepository
import javax.inject.Inject

class GetMovieFramesUseCase @Inject constructor(
    private val repository: FrameRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.getMovieFrames(movieId)
}