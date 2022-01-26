package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.VideoRepository
import javax.inject.Inject

class FetchMovieVideosUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(movieId: Long) = repository.fetchMovieVideos(movieId)
}