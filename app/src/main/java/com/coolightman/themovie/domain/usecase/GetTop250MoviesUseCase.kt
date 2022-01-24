package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.ShortMovieRepository
import javax.inject.Inject

class GetTop250MoviesUseCase @Inject constructor(
    private val repository: ShortMovieRepository
) {
    operator fun invoke() = repository.getTop250Movies()
}