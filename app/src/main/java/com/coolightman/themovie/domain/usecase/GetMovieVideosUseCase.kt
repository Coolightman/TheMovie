package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.VideoRepository

class GetMovieVideosUseCase(
    private val repository: VideoRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovieVideos(movieId)
}