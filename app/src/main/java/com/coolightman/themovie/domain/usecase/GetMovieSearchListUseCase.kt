package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieSearchRepository
import javax.inject.Inject

class GetMovieSearchListUseCase @Inject constructor(
    private val repository: MovieSearchRepository
) {
    operator fun invoke() = repository.getMovieSearchList()
}