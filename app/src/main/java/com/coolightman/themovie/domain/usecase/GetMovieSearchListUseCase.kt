package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieSearchListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMovieSearchList()
}