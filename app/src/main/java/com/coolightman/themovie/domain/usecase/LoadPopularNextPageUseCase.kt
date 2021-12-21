package com.coolightman.themovie.domain.usecase

import com.coolightman.themovie.domain.repository.PageRepository
import javax.inject.Inject

class LoadPopularNextPageUseCase @Inject constructor(
    private val repository: PageRepository
) {
    suspend operator fun invoke() = repository.loadPopularNextPage()
}