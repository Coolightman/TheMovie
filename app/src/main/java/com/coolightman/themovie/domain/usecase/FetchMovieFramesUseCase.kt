package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FrameRepository
import javax.inject.Inject

class FetchMovieFramesUseCase @Inject constructor(
    private val repository: FrameRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.fetchMovieFrames(movieId)
}