package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.ShortMovieRepository
import javax.inject.Inject

class ClearTop250MoviesUseCase @Inject constructor(
    private val repository: ShortMovieRepository
) {
    suspend operator fun invoke() = repository.clearTop250Movies()
}
