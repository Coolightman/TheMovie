package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Long) = repository.getMovie(movieId)
}