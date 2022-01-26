package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.MovieSearchRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieSearchRepository
) {
    suspend operator fun invoke(keywords: String) = repository.searchMovies(keywords)
}