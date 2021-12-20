package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.FrameRepository

class GetMovieFramesUseCase(
    private val repository: FrameRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieFrames(movieId)
}