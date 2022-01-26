package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.ShortMovieRepository
import javax.inject.Inject

class GetTop250PlaceUseCase @Inject constructor(
    private val repository: ShortMovieRepository
) {
    operator fun invoke(movieId: Long) = repository.getTop250Place(movieId)
}