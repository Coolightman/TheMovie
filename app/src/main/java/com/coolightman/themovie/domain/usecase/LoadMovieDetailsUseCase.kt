package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository
import com.coolightman.themovie.domain.repository.PageRepository
import javax.inject.Inject

class LoadMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Long) = movieRepository.loadMovie(movieId)
}